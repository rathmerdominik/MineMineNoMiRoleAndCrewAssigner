package net.hammerclock.roleassigner.event;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.erdbeerbaerlp.dcintegration.common.DiscordIntegration;
import de.erdbeerbaerlp.dcintegration.common.storage.linking.LinkManager;
import de.erdbeerbaerlp.dcintegration.common.storage.linking.PlayerLink;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.hammerclock.roleassigner.RoleAssigner;
import net.hammerclock.roleassigner.config.CommonConfig;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import xyz.pixelatedw.mineminenomi.api.events.SetPlayerDetailsEvent;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModValues;

public class RoleAssignerEvent {
	public static final Logger LOGGER = LogManager.getLogger(RoleAssigner.PROJECT_ID);
	public static final CommonConfig CONFIG = CommonConfig.INSTANCE;

	@SubscribeEvent
	public void onPlayerDetailsSet(SetPlayerDetailsEvent event) {
		if (LinkManager.isPlayerLinked(event.getPlayer().getUUID())) {
			PlayerLink playerLink = LinkManager.getLink(null, event.getPlayer().getUUID());
			deleteRolesFromPlayer(playerLink);
			setPlayerRoles(event.getEntityStats(), playerLink);
		} else {
			LOGGER.warn(
					String.format(
							"User with the UUID %s is not Linked! Consider enforcing linking in Discord Integrations Config. Cannot set Roles!",
							event.getPlayer().getUUID()));
		}
	}

	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		if (LinkManager.isPlayerLinked(event.getPlayer().getUUID())) {
			IEntityStats entityStats = EntityStatsCapability.get(event.getPlayer());
			PlayerLink playerLink = LinkManager.getLink(null, event.getPlayer().getUUID());
			deleteRolesFromPlayer(playerLink);
			setPlayerRoles(entityStats, playerLink);
		} else {
			LOGGER.warn(
					String.format(
							"User with the UUID %s is not Linked! Consider enforcing linking in Discord Integrations Config. Cannot set Roles!",
							event.getPlayer().getUUID()));
		}
	}

	public void setPlayerRoles(IEntityStats entityStats, PlayerLink player) {
		Guild guild = DiscordIntegration.INSTANCE.getChannel().getGuild();
		Member member = DiscordIntegration.INSTANCE.getMemberById(player.discordID);

		switch (entityStats.getFaction()) {
			case ModValues.PIRATE:
				ignoreOrSetRole(guild, CONFIG.getPirateRoleId(), member);
				break;
			case ModValues.MARINE:
				ignoreOrSetRole(guild, CONFIG.getMarineRoleId(), member);
				break;
			case ModValues.BOUNTY_HUNTER:
				ignoreOrSetRole(guild, CONFIG.getBountyHunterRoleId(), member);
				break;
			case ModValues.REVOLUTIONARY:
				ignoreOrSetRole(guild, CONFIG.getRevolutionArmyRoleId(), member);
				break;
		}

		switch (entityStats.getRace()) {
			case ModValues.HUMAN:
				ignoreOrSetRole(guild, CONFIG.getHumanRoleId(), member);
				break;
			case ModValues.FISHMAN:
				ignoreOrSetRole(guild, CONFIG.getFishManRoleId(), member);
				break;
			case ModValues.CYBORG:
				ignoreOrSetRole(guild, CONFIG.getCyborgRoleId(), member);
				break;
			case ModValues.MINK:
				ignoreOrSetRole(guild, CONFIG.getMinkRoleId(), member);
				break;
		}

		switch (entityStats.getFightingStyle()) {
			case ModValues.SWORDSMAN:
				ignoreOrSetRole(guild, CONFIG.getSwordsManRoleId(), member);
				break;
			case ModValues.SNIPER:
				ignoreOrSetRole(guild, CONFIG.getSniperRoleId(), member);
				break;
			case ModValues.DOCTOR:
				ignoreOrSetRole(guild, CONFIG.getDoctorRoleId(), member);
				break;
			case ModValues.ART_OF_WEATHER:
				ignoreOrSetRole(guild, CONFIG.getArtOfWeatherRoleId(), member);
				break;
			case ModValues.BLACK_LEG:
				ignoreOrSetRole(guild, CONFIG.getBlackLegRoleId(), member);
				break;
			case ModValues.BRAWLER:
				ignoreOrSetRole(guild, CONFIG.getBrawlerRoleId(), member);
				break;
		}
	}

	private void ignoreOrSetRole(Guild guild, Long roleId, Member member) {
		if (roleId != 0l) {
			guild.addRoleToMember(UserSnowflake.fromId(member.getUser().getId()),
					DiscordIntegration.INSTANCE.getJDA().getRoleById(roleId)).queue();
		}
	}

	private void deleteRolesFromPlayer(PlayerLink player) {
		Guild guild = DiscordIntegration.INSTANCE.getChannel().getGuild();
		Member member = DiscordIntegration.INSTANCE.getMemberById(player.discordID);

		List<Role> memberRoles = member.getRoles();

		for (Role roleId : memberRoles) {
			if (CONFIG.getAllRoleIds().contains(roleId.getIdLong())) {
				guild.removeRoleFromMember(UserSnowflake.fromId(member.getUser().getId()), roleId).queue();
			}
		}
	}

}
