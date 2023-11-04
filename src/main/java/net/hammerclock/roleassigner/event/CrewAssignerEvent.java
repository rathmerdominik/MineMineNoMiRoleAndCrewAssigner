package net.hammerclock.roleassigner.event;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dcshadow.org.jetbrains.annotations.Nullable;
import de.erdbeerbaerlp.dcintegration.common.DiscordIntegration;
import de.erdbeerbaerlp.dcintegration.common.storage.linking.LinkManager;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.utils.TimeFormat;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageEditData;
import net.dv8tion.jda.api.utils.FileUpload;

import net.hammerclock.roleassigner.RoleAssigner;
import net.hammerclock.roleassigner.config.CommonConfig;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import xyz.pixelatedw.mineminenomi.api.crew.Crew;
import xyz.pixelatedw.mineminenomi.api.crew.JollyRoger;
import xyz.pixelatedw.mineminenomi.api.crew.Crew.Member;
import xyz.pixelatedw.mineminenomi.api.events.CrewEvent;
import xyz.pixelatedw.mineminenomi.api.events.JollyRogerEvent;
import xyz.pixelatedw.mineminenomi.data.world.ExtendedWorldData;

public class CrewAssignerEvent {
	public static final Logger LOGGER = LogManager.getLogger(RoleAssigner.PROJECT_ID);
	public static final CommonConfig CONFIG = CommonConfig.INSTANCE;

	@SubscribeEvent
	public void onServerStartedEvent(FMLServerStartedEvent event) {
		for (Crew crew : ExtendedWorldData.get().getCrews()) {
			if (crew.getMembers().size() <= 0) {
				continue;
			}
			this.createOrUpdateCrew(crew, null, null, null, null);
		}
	}

	@SubscribeEvent
	public void onJollyRogerUpdate(JollyRogerEvent.Update event) {
		this.createOrUpdateCrew(event.getCrew(), event.getJollyRoger(), null, null, null);
	}

	@SubscribeEvent
	public void onCrewCreated(CrewEvent.Create event) {
		this.createOrUpdateCrew(event.getCrew(), null, null, null, null);
	}

	@SubscribeEvent
	public void onCrewJoined(CrewEvent.Join event) {
		if (event.getCrew().hasMember(event.getPlayer().getUUID())) {
			return;
		}
		this.createOrUpdateCrew(event.getCrew(), null, null, event.getPlayer(), null);
	}

	@SubscribeEvent
	public void onCrewLeave(CrewEvent.Leave event) {
		if (event.getCrew().getMembers().size() - 1 <= 0) {
			this.createOrUpdateCrew(event.getCrew(), null, true, null, null);
		} else {
			this.createOrUpdateCrew(event.getCrew(), null, null, null, event.getPlayer());
		}
	}

	@SubscribeEvent
	public void onCrewKick(CrewEvent.Kick event) {
		this.createOrUpdateCrew(event.getCrew(), null, null, null, event.getPlayer());
	}

	private void createOrUpdateCrew(Crew crew, @Nullable JollyRoger jollyRoger, @Nullable Boolean deleteChannel,
			@Nullable PlayerEntity playerToJoin, @Nullable PlayerEntity playerToLeave) {

		Guild GUILD = DiscordIntegration.INSTANCE.getChannel().getGuild();
		Long existentChannel = 0L;
		List<Member> members = crew.getMembers();
		Member crewCaptain = crew.getCaptain();

		if (playerToJoin != null) {
			crew.addMember(playerToJoin);
		}

		if (playerToLeave != null) {
			crew.removeMember(playerToLeave.getUUID());
		}

		if (CONFIG.getCrewForumChannelId() != 0L) {
			ForumChannel channel = GUILD.getForumChannelById(CONFIG.getCrewForumChannelId());

			for (ThreadChannel threadChannel : channel.getThreadChannels()) {
				if (threadChannel.getName().equals(crew.getName())) {
					existentChannel = threadChannel.getIdLong();
					if (deleteChannel != null || members.size() <= 0) {
						threadChannel.delete().queue();
						this.reAddOrDeletePlayer(crew, playerToJoin, playerToLeave);
						return;
					}
				}
			}

			String messageContent = "";
			if (CONFIG.getShowCaptain()) {
				messageContent += String.format("**Captain:** %s | %s\n\n",
						crewCaptain.getUsername(),
						getDiscordLinkOrNotLinked(crewCaptain));
			}

			if (CONFIG.getSyncCrewMembers()) {
				messageContent = this.addCrewMembersToMessageContent(messageContent, crew, members, crewCaptain);
			}

			if (CONFIG.getShowCreationDate()) {
				messageContent += String.format("**Creation Date:** %s", TimeFormat.DATE_TIME_SHORT.now());
			}

			MessageCreateBuilder messageBuilder = new MessageCreateBuilder().setContent(messageContent);

			if (CONFIG.getSyncCrewBanner() && crew.getJollyRoger().getAsBufferedImage().isPresent()) {
				if (jollyRoger == null) {
					jollyRoger = crew.getJollyRoger();
				}

				messageBuilder = this.addJollyRogerToMessageBuilder(messageBuilder, jollyRoger);
			}

			if (existentChannel != 0L) {
				ThreadChannel threadChannel = GUILD.getThreadChannelById(existentChannel);
				Message startMessage = threadChannel.retrieveStartMessage().complete();

				startMessage.editMessage(MessageEditData.fromCreateData(messageBuilder.build())).queue();
				this.reAddOrDeletePlayer(crew, playerToJoin, playerToLeave);
				return;
			}

			channel.createForumPost(crew.getName(),
					messageBuilder.build())
					.queue();
			this.reAddOrDeletePlayer(crew, playerToJoin, playerToLeave);

		}
	}

	private void reAddOrDeletePlayer(Crew crew, @Nullable PlayerEntity playerToJoin,
			@Nullable PlayerEntity playerToLeave) {
		if (playerToJoin != null) {
			crew.removeMember(playerToJoin.getUUID());
		}

		if (playerToLeave != null) {
			crew.addMember(playerToLeave);
		}
	}

	private String addCrewMembersToMessageContent(String messageContent, Crew crew, List<Member> members,
			Member captain) {
		for (Member crewMember : members) {
			if (CONFIG.getShowCaptain() && crewMember.getUsername() == captain.getUsername())
				continue;

			messageContent += String.format("**Member:** %s | %s\n",
					crewMember.getUsername(),
					getDiscordLinkOrNotLinked(crewMember));
		}

		if (members.size() > 1 || !CONFIG.getShowCaptain()) {
			messageContent += "\n";
		}

		return messageContent;
	}

	private MessageCreateBuilder addJollyRogerToMessageBuilder(MessageCreateBuilder messageBuilder,
			JollyRoger jollyRoger) {
		BufferedImage jollyRogerImage = jollyRoger.getAsBufferedImage().get();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(jollyRogerImage, "png", os);
		} catch (IOException e) {
			LOGGER.error("This is awkward. This should never be called in the first place.");
		}
		InputStream is = new ByteArrayInputStream(os.toByteArray());

		return messageBuilder.setFiles(FileUpload.fromData(is, "jollyRoger.png"));
	}

	private String getDiscordLinkOrNotLinked(Member member) {
		return LinkManager.isPlayerLinked(member.getUUID())
				? DiscordIntegration.INSTANCE.getMemberById(
						LinkManager.getLink(null, member.getUUID()).discordID)
						.getAsMention()
				: "NO DISCORD LINK";
	}

}
