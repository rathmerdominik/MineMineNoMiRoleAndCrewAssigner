package net.hammerclock.rolencrewassigner.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;

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

	// Marine Ranks
	private ForgeConfigSpec.LongValue marineChoreBoyRoleId;
	private ForgeConfigSpec.LongValue marineSeaManRoleId;
	private ForgeConfigSpec.LongValue marinePettyOfficerRoleId;
	private ForgeConfigSpec.LongValue marineLieutenantRoleId;
	private ForgeConfigSpec.LongValue marineCommanderRoleId;
	private ForgeConfigSpec.LongValue marineCaptainRoleId;
	private ForgeConfigSpec.LongValue marineCommodoreRoleId;
	private ForgeConfigSpec.LongValue marineViceAdmiralRoleId;
	private ForgeConfigSpec.LongValue marineAdmiralRoleId;
	private ForgeConfigSpec.LongValue marineFleetAdmiralRoleId;

	// Revolutionary Ranks
	private ForgeConfigSpec.LongValue revoMemberRoleId;
	private ForgeConfigSpec.LongValue revoOfficerRoleId;
	private ForgeConfigSpec.LongValue revoCommanderRoleId;
	private ForgeConfigSpec.LongValue revoChiefRoleId;
	private ForgeConfigSpec.LongValue revoSupremeCommanderRoleId;


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
		
		this.minkRoleId = builder.defineInRange("Mink Role Id", 0L, 0L, Long.MAX_VALUE);
		this.cyborgRoleId = builder.defineInRange("Cyborg Role Id", 0L, 0L, Long.MAX_VALUE);
		this.fishManRoleId = builder.defineInRange("Fishman Role Id", 0L, 0L, Long.MAX_VALUE);
		this.humanRoleId = builder.defineInRange("Human Role Id", 0L, 0L, Long.MAX_VALUE);
		builder.pop();

		builder.push("Factions");
		this.marineRoleId = builder.defineInRange("Marine Role Id", 0L, 0L, Long.MAX_VALUE);
		this.revolutionArmyRoleId = builder.defineInRange("Revolution Army Role Id", 0L, 0L, Long.MAX_VALUE);
		this.bountyHunterRoleId = builder.defineInRange("Bounty Hunter Role Id", 0L, 0L, Long.MAX_VALUE);
		this.pirateRoleId = builder.defineInRange("Pirate Role Id", 0L, 0L, Long.MAX_VALUE);
		builder.pop();

		builder.push("Fighting Styles");
		this.blackLegRoleId = builder.defineInRange("Black Leg Role Id", 0L, 0L, Long.MAX_VALUE);
		this.artOfWeatherRoleId = builder.defineInRange("Art of Weather Role Id", 0L, 0L, Long.MAX_VALUE);
		this.brawlerRoleId = builder.defineInRange("Brawler Role Id", 0L, 0L, Long.MAX_VALUE);
		this.sniperRoleId = builder.defineInRange("Sniper Role Id", 0L, 0L, Long.MAX_VALUE);
		this.doctorRoleId = builder.defineInRange("Doctor Role Id", 0L, 0L, Long.MAX_VALUE);
		this.swordsManRoleId = builder.defineInRange("Swords Man Role Id", 0L, 0L, Long.MAX_VALUE);
		builder.pop();

		builder.push("Pirate Crews");
		this.crewForumChannelId = builder
				.comment("Has to be a Forum channel! Therefore your server MUST be a community server if you want to use this feature!")
				.defineInRange("Crew Forum Channel Id", 0L, 0L, Long.MAX_VALUE);
		this.syncCrewBanner = builder.define("Sync Crew Banner", true);
		this.syncCrewMembers = builder.define("Sync Crew Members", true);
		this.showCaptain = builder.define("Show Captain of the crew", true);
		this.showCreationDate = builder.define("Show Crew Creation Date", true);
		builder.pop();

		builder.push("Marine Ranks");
		this.marineChoreBoyRoleId = builder.defineInRange("Marine Chore Boy Role Id", 0L, 0L, Long.MAX_VALUE);
		this.marineSeaManRoleId = builder.defineInRange("Marine Sea Man Role Id", 0L, 0L, Long.MAX_VALUE);
		this.marinePettyOfficerRoleId = builder.defineInRange("Marine Petty Officer Role Id", 0L, 0L, Long.MAX_VALUE);
		this.marineLieutenantRoleId = builder.defineInRange("Marine Lieutenant Role Id", 0L, 0L, Long.MAX_VALUE);
		this.marineCommanderRoleId = builder.defineInRange("Marine Commander Role Id", 0L, 0L, Long.MAX_VALUE);
		this.marineCaptainRoleId = builder.defineInRange("Marine Captain Role Id", 0L, 0L, Long.MAX_VALUE);
		this.marineCommodoreRoleId = builder.defineInRange("Marine Commodore Role Id", 0L, 0L, Long.MAX_VALUE);
		this.marineViceAdmiralRoleId= builder.defineInRange("Marine Vice Admiral Man Role Id", 0L, 0L, Long.MAX_VALUE);
		this.marineAdmiralRoleId = builder.defineInRange("Marine Admiral Role Id", 0L, 0L, Long.MAX_VALUE);
		this.marineFleetAdmiralRoleId = builder.defineInRange("Marine Fleet Admiral Role Id", 0L, 0L, Long.MAX_VALUE);
		builder.pop();

		builder.push("Revolutionary Ranks");
		this.revoMemberRoleId = builder.defineInRange("Revolutionary Army Member Role Id", 0L, 0L, Long.MAX_VALUE);
		this.revoOfficerRoleId = builder.defineInRange("Revolutionary Army Officer Role Id", 0L, 0L, Long.MAX_VALUE);
		this.revoCommanderRoleId = builder.defineInRange("Revolutionary Army Commander Role Id", 0L, 0L, Long.MAX_VALUE);
		this.revoChiefRoleId = builder.defineInRange("Revolutionary Army Chief of Staff Role Id", 0L, 0L, Long.MAX_VALUE);
		this.revoSupremeCommanderRoleId = builder.defineInRange("Revolutionary Army Supreme Commander Role Id", 0L, 0L, Long.MAX_VALUE);
		builder.pop();
	}

	// Race getters

	public long getMinkRoleId() {
		return minkRoleId.get();
	}

	public long getCyborgRoleId() {
		return cyborgRoleId.get();
	}

	public long getFishManRoleId() {
		return fishManRoleId.get();
	}

	public long getHumanRoleId() {
		return humanRoleId.get();
	}

	// Factions getters

	public long getMarineRoleId() {
		return marineRoleId.get();
	}

	public long getRevolutionArmyRoleId() {
		return revolutionArmyRoleId.get();
	}

	public long getBountyHunterRoleId() {
		return bountyHunterRoleId.get();
	}

	public long getPirateRoleId() {
		return pirateRoleId.get();
	}

	// Fighting Style getters

	public long getBlackLegRoleId() {
		return blackLegRoleId.get();
	}

	public long getArtOfWeatherRoleId() {
		return artOfWeatherRoleId.get();
	}

	public long getBrawlerRoleId() {
		return brawlerRoleId.get();
	}

	public long getSniperRoleId() {
		return sniperRoleId.get();
	}

	public long getDoctorRoleId() {
		return doctorRoleId.get();
	}

	public long getSwordsManRoleId() {
		return swordsManRoleId.get();
	}

	// Crew getters
	
	public long getCrewForumChannelId() {
		return crewForumChannelId.get();
	}

	public boolean getSyncCrewMembers() {
		return syncCrewMembers.get();
	}

	public boolean getSyncCrewBanner() {
		return syncCrewBanner.get();
	}

	public boolean getShowCaptain() {
		return showCaptain.get();
	}

	public boolean getShowCreationDate() {
		return showCreationDate.get();
	}

	// Marine getters

	public long getMarineChoreBoyRoleId() {
		return marineChoreBoyRoleId.get();
	}

	public long getMarineSeaManRoleId() {
		return marineSeaManRoleId.get();
	}

	public long getMarinePettyOfficerRoleId() {
		return marinePettyOfficerRoleId.get();
	}

	public long getMarineLieutenantRoleId() {
		return marineLieutenantRoleId.get();
	}

	public long getMarineCommanderRoleId() {
		return marineCommanderRoleId.get();
	}

	public long getMarineCaptainRoleId() {
		return marineCaptainRoleId.get();
	}

	public long getMarineCommodoreRoleId() {
		return marineCommodoreRoleId.get();
	}

	public long getMarineViceAdmiralRoleId() {
		return marineViceAdmiralRoleId.get();
	}

	public long getMarineAdmiralRoleId() {
		return marineAdmiralRoleId.get();
	}

	public long getMarineFleetAdmiralRoleId() {
		return marineFleetAdmiralRoleId.get();
	}

	// Revolutionary Army getters

	public long getRevoMemberRoleId() {
		return revoMemberRoleId.get();
	}

	public long getRevoOfficerRoleId() {
		return revoOfficerRoleId.get();
	}

	public long getRevoCommanderRoleId() {
		return revoCommanderRoleId.get();
	}

	public long getRevoChiefRoleId() {
		return revoChiefRoleId.get();
	}

	public long getRevoSupremeCommanderRoleId() {
		return revoSupremeCommanderRoleId.get();
	}

	public Set<Long> getAllRoleIds() {
		return new HashSet<>(Arrays.asList(
				this.getArtOfWeatherRoleId(), 
				this.getBlackLegRoleId(),
				this.getBountyHunterRoleId(), 
				this.getBrawlerRoleId(), 
				this.getCyborgRoleId(), 
				this.getDoctorRoleId(),
				this.getFishManRoleId(), 
				this.getHumanRoleId(), 
				this.getMarineRoleId(), 
				this.getMinkRoleId(),
				this.getPirateRoleId(), 
				this.getRevolutionArmyRoleId(), 
				this.getSniperRoleId(),
				this.getSwordsManRoleId(),
				this.getMarineChoreBoyRoleId(),
				this.getMarineSeaManRoleId(),
				this.getMarinePettyOfficerRoleId(),
				this.getMarineLieutenantRoleId(),
				this.getMarineCommanderRoleId(),
				this.getMarineCaptainRoleId(),
				this.getMarineCommodoreRoleId(),
				this.getMarineViceAdmiralRoleId(),
				this.getMarineAdmiralRoleId(),
				this.getMarineFleetAdmiralRoleId(),
				this.getRevoMemberRoleId(),
				this.getRevoOfficerRoleId(),
				this.getRevoCommanderRoleId(),
				this.getRevoChiefRoleId(),
				this.getRevoSupremeCommanderRoleId()
			)
		);
	}

}
