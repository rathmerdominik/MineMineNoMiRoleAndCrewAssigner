package net.hammerclock.roleassigner.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
	public static final Path CONFIG_PATH = Paths.get("config", "mmnm-roleassign-common.toml");
	public static final CommonConfig INSTANCE;
	public static final ForgeConfigSpec CONFIG;

	// Races
	private ForgeConfigSpec.ConfigValue<Long> minkRoleId;
	private ForgeConfigSpec.ConfigValue<Long> cyborgRoleId;
	private ForgeConfigSpec.ConfigValue<Long> fishManRoleId;
	private ForgeConfigSpec.ConfigValue<Long> humanRoleId;

	// Factions
	private ForgeConfigSpec.ConfigValue<Long> marineRoleId;
	private ForgeConfigSpec.ConfigValue<Long> revolutionArmyRoleId;
	private ForgeConfigSpec.ConfigValue<Long> bountyHunterRoleId;
	private ForgeConfigSpec.ConfigValue<Long> pirateRoleId;

	// Fighting styles
	private ForgeConfigSpec.ConfigValue<Long> blackLegRoleId;
	private ForgeConfigSpec.ConfigValue<Long> artOfWeatherRoleId;
	private ForgeConfigSpec.ConfigValue<Long> brawlerRoleId;
	private ForgeConfigSpec.ConfigValue<Long> sniperRoleId;
	private ForgeConfigSpec.ConfigValue<Long> doctorRoleId;
	private ForgeConfigSpec.ConfigValue<Long> swordsManRoleId;

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
		this.minkRoleId = builder.define("Mink Role Id", 0L);
		this.cyborgRoleId = builder.define("Cyborg Role Id", 0L);
		this.fishManRoleId = builder.define("Fishman Role Id", 0L);
		this.humanRoleId = builder.define("Human Role Id", 0L);

		builder.pop();
		builder.push("Factions");
		this.marineRoleId = builder.define("Marine Role Id", 0L);
		this.revolutionArmyRoleId = builder.define("Revolution Army Role Id", 0L);
		this.bountyHunterRoleId = builder.define("Bounty Hunter Role Id", 0L);
		this.pirateRoleId = builder.define("Pirate Role Id", 0L);

		builder.pop();
		builder.push("Fighting Styles");
		this.blackLegRoleId = builder.define("Black Leg Role Id", 0L);
		this.artOfWeatherRoleId = builder.define("Art of Weather Role Id", 0L);
		this.brawlerRoleId = builder.define("Brawler Role Id", 0L);
		this.sniperRoleId = builder.define("Sniper Role Id", 0L);
		this.doctorRoleId = builder.define("Doctor Role Id", 0L);
		this.swordsManRoleId = builder.define("Swords Man Role Id", 0L);

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

}
