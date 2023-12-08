package net.hammerclock.rolencrewassigner.event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.erdbeerbaerlp.dcintegration.common.DiscordIntegration;
import de.erdbeerbaerlp.dcintegration.common.storage.linking.LinkManager;
import de.erdbeerbaerlp.dcintegration.common.storage.linking.PlayerLink;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.hammerclock.rolencrewassigner.RoleAndCrewAssigner;
import net.hammerclock.rolencrewassigner.config.CommonConfig;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import xyz.pixelatedw.mineminenomi.api.events.SetPlayerDetailsEvent;
import xyz.pixelatedw.mineminenomi.api.events.stats.LoyaltyEvent;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.entitystats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModValues;

public class RoleAssignerEvent {
	public static final Logger LOGGER = LogManager.getLogger(RoleAndCrewAssigner.PROJECT_ID);
	public static final CommonConfig CONFIG = CommonConfig.INSTANCE;

	@SubscribeEvent
	public void onPlayerDetailsSet(SetPlayerDetailsEvent event) {
		Optional<PlayerLink> playerLink = getOrWarnPlayerLink(event.getPlayer().getUUID());
		if (playerLink.isPresent()) {
			deleteRolesFromPlayer(playerLink.get());
			setPlayerRoles(event.getEntityStats(), playerLink.get());
		}
	}

	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
		Optional<PlayerLink> playerLink = getOrWarnPlayerLink(event.getPlayer().getUUID());
		if (playerLink.isPresent()) {
			IEntityStats entityStats = EntityStatsCapability.get(event.getPlayer());
			deleteRolesFromPlayer(playerLink.get());
			setPlayerRoles(entityStats, playerLink.get());
		}
	}

	@SubscribeEvent
	public void onLoyaltyChange(LoyaltyEvent.Post event) {
		Optional<PlayerLink> playerLink = getOrWarnPlayerLink(event.getPlayer().getUUID());
		if (playerLink.isPresent()) {
			IEntityStats entityStats = EntityStatsCapability.get(event.getPlayer());
			deleteRolesFromPlayer(playerLink.get());
			setPlayerRoles(entityStats, playerLink.get());
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
				this.setMarineRankRoles(guild, entityStats, member);
				break;
			case ModValues.BOUNTY_HUNTER:
				ignoreOrSetRole(guild, CONFIG.getBountyHunterRoleId(), member);
				break;
			case ModValues.REVOLUTIONARY:
				ignoreOrSetRole(guild, CONFIG.getRevolutionArmyRoleId(), member);
				this.setRevoRankRoles(guild, entityStats, member);
				break;
			default:
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
			default:
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
			default:
				break;
		}
	}

	private void setMarineRankRoles(Guild guild, IEntityStats entityStats, Member member) {
		switch (entityStats.getMarineRank()) {
			case CHORE_BOY:
				ignoreOrSetRole(guild, CONFIG.getMarineChoreBoyRoleId(), member);
				break;
			case SEAMAN:
				ignoreOrSetRole(guild, CONFIG.getMarineSeaManRoleId(), member);
				break;
			case PETTY_OFFICER:
				ignoreOrSetRole(guild, CONFIG.getMarinePettyOfficerRoleId(), member);
				break;
			case LIEUTENANT:
				ignoreOrSetRole(guild, CONFIG.getMarineLieutenantRoleId(), member);
				break;
			case COMMANDER:
				ignoreOrSetRole(guild, CONFIG.getMarineCommanderRoleId(), member);
				break;
			case CAPTAIN:
				ignoreOrSetRole(guild, CONFIG.getMarineCaptainRoleId(), member);
				break;
			case COMMODORE:
				ignoreOrSetRole(guild, CONFIG.getMarineCommodoreRoleId(), member);
				break;
			case VICE_ADMIRAL:
				ignoreOrSetRole(guild, CONFIG.getMarineViceAdmiralRoleId(), member);
				break;
			case ADMIRAL:
				ignoreOrSetRole(guild, CONFIG.getMarineAdmiralRoleId(), member);
				break;
			case FLEET_ADMIRAL:
				ignoreOrSetRole(guild, CONFIG.getMarineFleetAdmiralRoleId(), member);
				break;
		}
	}

	private void setRevoRankRoles(Guild guild, IEntityStats entityStats, Member member) {
		switch (entityStats.getRevolutionaryRank()) {
			case MEMBER:
				ignoreOrSetRole(guild, CONFIG.getRevoMemberRoleId(), member);
				break;
			case OFFICER:
				ignoreOrSetRole(guild, CONFIG.getRevoOfficerRoleId(), member);
				break;
			case COMMANDER:
				ignoreOrSetRole(guild, CONFIG.getRevoCommanderRoleId(), member);
				break;
			case CHIEF_OF_STAFF:
				ignoreOrSetRole(guild, CONFIG.getRevoChiefRoleId(), member);
				break;
			case SUPREME_COMMANDER:
				ignoreOrSetRole(guild, CONFIG.getRevoSupremeCommanderRoleId(), member);
				break;
		}
	}

	public Optional<PlayerLink> getOrWarnPlayerLink(UUID uuid) {
		if (LinkManager.isPlayerLinked(uuid)) {
			return Optional.of(LinkManager.getLink(null, uuid));
		} else {
			LOGGER.warn(
				"User with the UUID {} is not Linked! Consider enforcing linking in Discord Integrations Config. Cannot set Roles!",
					uuid
				);
		}
		return Optional.empty();
	}

	private void ignoreOrSetRole(Guild guild, Long roleId, Member member) {
		if (roleId != 0L && roleId != null) {
			Role foundRole = DiscordIntegration.INSTANCE.getJDA().getRoleById(roleId);
			if(foundRole == null) {
				LOGGER.error("Role could not be set because it cant be found. Role: {}", roleId);
				return;
			}
			guild.addRoleToMember(UserSnowflake.fromId(member.getUser().getId()), foundRole).queue();
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
