package net.hammerclock.roleassigner.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.LongValue;

public class CommonConfig {
	public static final Path CONFIG_PATH = Paths.get("config", "mmnm-roleassign-common.toml");
	public static final CommonConfig INSTANCE;
	public static final ForgeConfigSpec CONFIG;

	// Races
	private ForgeConfigSpec.LongValue minkRoleId;
	private ForgeConfigSpec.LongValue cyborgRoleId;
	private ForgeConfigSpec.LongValue fishManRoleId;
	private ForgeConfigSpec.LongValue humanRoleId;

	// Factions
	private ForgeConfigSpec.LongValue marineRoleId;
	private ForgeConfigSpec.BooleanValue syncMarineRanks;
	private ForgeConfigSpec.LongValue revolutionArmyRoleId;
	private ForgeConfigSpec.LongValue bountyHunterRoleId;
	private ForgeConfigSpec.LongValue pirateRoleId;

	// Fighting styles
	private ForgeConfigSpec.LongValue blackLegRoleId;
	private ForgeConfigSpec.LongValue artOfWeatherRoleId;
	private ForgeConfigSpec.LongValue brawlerRoleId;
	private ForgeConfigSpec.LongValue sniperRoleId;
	private ForgeConfigSpec.LongValue doctorRoleId;
	private ForgeConfigSpec.LongValue swordsManRoleId;

	// Pirate Crews
	private ForgeConfigSpec.LongValue crewForumChannelId;
	private ForgeConfigSpec.BooleanValue syncCrewMembers;
	private ForgeConfigSpec.BooleanValue syncCrewBanner;
	private ForgeConfigSpec.BooleanValue showCaptain;
	private ForgeConfigSpec.BooleanValue showCreationDate;

	static {
		Pair<CommonConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);

		CONFIG = pair.getRight();
		INSTANCE = pair.getLeft();

		CommentedFileConfig file = CommentedFileConfig.builder(CONFIG_PATH).sync().autoreload()
				.writingMode(WritingMode.REPLACE).build();

		file.load();
		file.save();

		CONFIG.setConfig(file);
	}

	public CommonConfig(ForgeConfigSpec.Builder builder) {
		builder.comment("To disable a specific Role from syncing just do not change the ID on the config option");

		builder.push("Races");
		this.minkRoleId = (LongValue) builder.define("Mink Role Id", 0L);
		this.cyborgRoleId = (LongValue) builder.define("Cyborg Role Id", 0L);
		this.fishManRoleId = (LongValue) builder.define("Fishman Role Id", 0L);
		this.humanRoleId = (LongValue) builder.define("Human Role Id", 0L);
		builder.pop();

		builder.push("Factions");
		this.marineRoleId = (LongValue) builder.define("Marine Role Id", 0L);
		this.revolutionArmyRoleId = (LongValue) builder.define("Revolution Army Role Id", 0L);
		this.bountyHunterRoleId = (LongValue) builder.define("Bounty Hunter Role Id", 0L);
		this.pirateRoleId = (LongValue) builder.define("Pirate Role Id", 0L);
		builder.pop();

		builder.push("Fighting Styles");
		this.blackLegRoleId = (LongValue) builder.define("Black Leg Role Id", 0L);
		this.artOfWeatherRoleId = (LongValue) builder.define("Art of Weather Role Id", 0L);
		this.brawlerRoleId = (LongValue) builder.define("Brawler Role Id", 0L);
		this.sniperRoleId = (LongValue) builder.define("Sniper Role Id", 0L);
		this.doctorRoleId = (LongValue) builder.define("Doctor Role Id", 0L);
		this.swordsManRoleId = (LongValue) builder.define("Swords Man Role Id", 0L);
		builder.pop();

		builder.push("Pirate Crews");
		this.crewForumChannelId = (LongValue) builder
				.comment("Has to be a Forum channel! Therefore your server MUST be a community server!")
				.define("Crew Forum Channel Id", 0L);
		this.syncCrewBanner = builder.define("Sync Crew Banner", true);
		this.syncCrewMembers = builder.define("Sync Crew Members", true);
		this.showCaptain = builder.define("Show Captain of the crew", true);
		this.showCreationDate = builder.define("Show Crew Creation Date", true);
		builder.pop();
	}

	public Long getMinkRoleId() {
		return minkRoleId.get();
	}

	public Long getCyborgRoleId() {
		return cyborgRoleId.get();
	}

	public Long getFishManRoleId() {
		return fishManRoleId.get();
	}

	public Long getHumanRoleId() {
		return humanRoleId.get();
	}

	public Long getMarineRoleId() {
		return marineRoleId.get();
	}

	public Long getRevolutionArmyRoleId() {
		return revolutionArmyRoleId.get();
	}

	public Long getBountyHunterRoleId() {
		return bountyHunterRoleId.get();
	}

	public Long getPirateRoleId() {
		return pirateRoleId.get();
	}

	public Long getBlackLegRoleId() {
		return blackLegRoleId.get();
	}

	public Long getArtOfWeatherRoleId() {
		return artOfWeatherRoleId.get();
	}

	public Long getBrawlerRoleId() {
		return brawlerRoleId.get();
	}

	public Long getSniperRoleId() {
		return sniperRoleId.get();
	}

	public Long getDoctorRoleId() {
		return doctorRoleId.get();
	}

	public Long getSwordsManRoleId() {
		return swordsManRoleId.get();
	}

	public Long getCrewForumChannelId() {
		return crewForumChannelId.get();
	}

	public Boolean getSyncCrewMembers() {
		return syncCrewMembers.get();
	}

	public Boolean getSyncCrewBanner() {
		return syncCrewBanner.get();
	}

	public Boolean getShowCaptain() {
		return showCaptain.get();
	}

	public Boolean getShowCreationDate() {
		return showCreationDate.get();
	}

	public Boolean getSyncMarineRanks() {
		return syncMarineRanks.get();
	}

	public HashSet<Long> getAllRoleIds() {

		HashSet<Long> roles = new HashSet<>(Arrays.asList(this.getArtOfWeatherRoleId(), this.getBlackLegRoleId(),
				this.getBountyHunterRoleId(), this.getBrawlerRoleId(), this.getCyborgRoleId(), this.getDoctorRoleId(),
				this.getFishManRoleId(), this.getHumanRoleId(), this.getMarineRoleId(), this.getMinkRoleId(),
				this.getPirateRoleId(), this.getRevolutionArmyRoleId(), this.getSniperRoleId(),
				this.getSwordsManRoleId()));

		return roles;

	}

}
