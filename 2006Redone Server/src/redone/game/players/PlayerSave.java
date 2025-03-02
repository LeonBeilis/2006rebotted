package redone.game.players;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import redone.util.Misc;

public class PlayerSave {

	/**
	 * Loading
	 **/
	public static int loadGame(Client player, String playerName, String playerPass) {
		return loadPlayerInfo(player, playerName, playerPass, true);
	}

	public static int loadPlayerInfo(Client player, String playerName, String playerPass, boolean doRealLogin)	{
		String line = "";
		String token = "";
		String token2 = "";
		String[] token3 = new String[3];
		boolean EndOfFile = false;
		int ReadMode = 0;
		BufferedReader characterfile = null;
		boolean File1 = false;

		try {
			characterfile = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/data/characters/" + player.playerName + ".txt"));
			File1 = true;
		} catch (FileNotFoundException fileex1) {
		}

		if (File1) {
			// new File ("./characters/"+playerName+".txt");
		} else {
			if (playerName.equals(""))
			{
				//it's the .gitignore :P
				return 0;
			}
			Misc.println(playerName + ": character file not found.");
			player.newPlayer = false;
			return 0;
		}
		try {
			line = characterfile.readLine();
		} catch (IOException ioexception) {
			Misc.println(playerName + ": error loading file.");
			return 3;
		}
		while (EndOfFile == false && line != null) {
			line = line.trim();
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot);
				token = token.trim();
				token2 = line.substring(spot + 1);
				token2 = token2.trim();
				token3 = token2.split("\t");
				switch (ReadMode) {
					case 1:
					    if (!doRealLogin)
							break;
						if (token.equals("character-password")) {
							if (playerPass.equalsIgnoreCase(token2)) {
								// Hash their password and store it!
								playerPass = passwordHash(token2);
							} else if (passwordHash(playerPass).equalsIgnoreCase(token2)) {
								playerPass = token2; //Valid password
							} else {
							    System.out.println("hash doesn't match: " + passwordHash(playerPass).toLowerCase());
							    System.out.println("currently is: " + passwordHash(token2).toLowerCase());
	 							return 3; //Invalid password
							}
						}
						break;
					case 2:
						switch (token) {
							case "character-height":
								player.heightLevel = Integer.parseInt(token2);
								break;
							case "character-posx":
								player.teleportToX = Integer.parseInt(token2) <= 0 ? player.lastX : Integer.parseInt(token2);
								break;
							case "character-posy":
								player.teleportToY = Integer.parseInt(token2) <= 0 ? player.lastY : Integer.parseInt(token2);
								break;
							case "character-rights":
								player.playerRights = Integer.parseInt(token2);
								break;
							case "blackMarks":
								player.blackMarks = Integer.parseInt(token2);
								break;
							case "lostCannon":
								player.lostCannon = Boolean.parseBoolean(token2);
								break;
							case "myBalls":
								player.getCannon().myBalls = Integer.parseInt(token2);
								break;
							case "cannonX":
								player.cannonX = Integer.parseInt(token2);
								break;
							case "cannonY":
								player.cannonY = Integer.parseInt(token2);
								break;
							case "removedTask0":
								player.removedTasks[0] = Integer.parseInt(token2);
								break;
							case "removedTask1":
								player.removedTasks[1] = Integer.parseInt(token2);
								break;
							case "removedTask2":
								player.removedTasks[2] = Integer.parseInt(token2);
								break;
							case "removedTask3":
								player.removedTasks[3] = Integer.parseInt(token2);
								break;
							case "SlayerMaster":
								player.SlayerMaster = Integer.parseInt(token2);
								break;
							case "slayerTask":
								player.slayerTask = Integer.parseInt(token2);
								break;
							case "slayerPoints":
								player.slayerPoints = Integer.parseInt(token2);
								break;
							case "taskAmount":
								player.taskAmount = Integer.parseInt(token2);
								break;
							case "cw-games":
								player.cwGames = Integer.parseInt(token2);
								break;
							case "crystal-bow-shots":
								player.crystalBowArrowCount = Integer.parseInt(token2);
								break;
							case "randomActions":
								player.randomActions = Integer.parseInt(token2);
								break;
							case "debugMode":
								player.debugMode = Boolean.parseBoolean(token2);
								break;
							case "global-damage":
								player.globalDamageDealt = Integer.parseInt(token2);
								break;
							case "skull-timer":
								player.skullTimer = Integer.parseInt(token2);
								break;
							case "recoilHits":
								player.recoilHits = Integer.parseInt(token2);
								break;
							case "brightness":
								player.brightness = Integer.parseInt(token2);
								break;
							case "spiritTree":
								player.spiritTree = Boolean.parseBoolean(token2);
								break;
							case "npcCanAttack":
								player.npcCanAttack = Boolean.parseBoolean(token2);
								break;
							case "rope":
								player.rope = Boolean.parseBoolean(token2);
								break;
							case "rope2":
								player.rope2 = Boolean.parseBoolean(token2);
								break;
							case "recievedMask":
								player.recievedMask = Boolean.parseBoolean(token2);
								break;
							case "recievedReward":
								player.recievedReward = Boolean.parseBoolean(token2);
								break;
							case "splitChat":
								player.splitChat = Boolean.parseBoolean(token2);
								break;
							case "hasPaid":
								player.hasPaid = Boolean.parseBoolean(token2);
								break;
							case "poison":
								player.poison = Boolean.parseBoolean(token2);
								break;
							case "closeTutorialInterface":
								player.closeTutorialInterface = Boolean
										.parseBoolean(token2);
								break;
							case "canWalkTutorial":
								player.canWalkTutorial = Boolean.parseBoolean(token2);
								break;
							case "needsNewTask":
								player.needsNewTask = Boolean.parseBoolean(token2);
								break;
							case "isBotting":
								player.isBotting = Boolean.parseBoolean(token2);
								break;
							case "musicOn":
								player.musicOn = Boolean.parseBoolean(token2);
								break;
							case "barrowsNpcs":
								player.barrowsNpcs[Integer.parseInt(token3[0])][1] = Integer.parseInt(token3[1]);
								break;
							case "summonId":
								player.summonId = Integer.parseInt(token2);
								break;
							case "has-npc":
								player.hasNpc = Boolean.parseBoolean(token2);
								break;
							case "barrowsKillCount":
								player.barrowsKillCount = Integer.parseInt(token2);
								break;
							case "luthas":
								player.luthas = Boolean.parseBoolean(token2);
								break;
							case "village":
								player.village = Boolean.parseBoolean(token2);
								break;
							case "lastThieve":
								player.lastThieve = Long.parseLong(token2);
								break;
							case "homeTele":
								player.homeTele = Long.parseLong(token2);
								break;
							case "tutorial-progress":
								player.tutorialProgress = Integer.parseInt(token2);
								break;
							case "strongHold":
								player.strongHold = Boolean.parseBoolean(token2);
								break;
							case "filter":
								player.filter = Boolean.parseBoolean(token2);
								break;
							case "ratdied2":
								player.ratdied2 = Boolean.parseBoolean(token2);
								break;
							case "randomToggle":
								player.randomEventsEnabled = Boolean.parseBoolean(token2);
								break;
							case "questStages":
								player.questStages = Integer.parseInt(token2);
								break;
							case "cookAss":
								player.cookAss = Integer.parseInt(token2);
								break;
							case "bananas":
								player.bananas = Integer.parseInt(token2);
								break;
							case "sheepShear":
								player.sheepShear = Integer.parseInt(token2);
								break;
							case "runeMist":
								player.runeMist = Integer.parseInt(token2);
								break;
							case "dragonSlayerQuestStage":
								player.dragonSlayerQuestStage = Integer.parseInt(token2);
								break;
							case "doricQuest":
								player.doricQuest = Integer.parseInt(token2);
								break;
							case "blackKnight":
								player.blackKnight = Integer.parseInt(token2);
								break;
							case "shieldArrav":
								player.shieldArrav = Integer.parseInt(token2);
								break;
							case "pirateTreasure":
								player.pirateTreasure = Integer.parseInt(token2);
								break;
							case "romeo-juliet":
								player.romeojuliet = Integer.parseInt(token2);
								break;
							case "vampSlayer":
								player.vampSlayer = Integer.parseInt(token2);
								break;
							case "gertCat":
								player.gertCat = Integer.parseInt(token2);
								break;
							case "witchspot":
								player.witchspot = Integer.parseInt(token2);
								break;
							case "restGhost":
								player.restGhost = Integer.parseInt(token2);
								break;
							case "impsC":
								player.impsC = Integer.parseInt(token2);
								break;
							case "knightS":
								player.knightS = Integer.parseInt(token2);
								break;
							case "lastX":
								player.lastX = Integer.parseInt(token2);
								break;
							case "lastY":
								player.lastY = Integer.parseInt(token2);
								break;
							case "lastH":
								player.lastH = Integer.parseInt(token2);
								break;
							case "hasStarter":
								player.hasStarter = Boolean.parseBoolean(token2);
								break;
							case "thankedForDonation":
								player.thankedForDonation = Integer.parseInt(token2);
								break;
							case "membership":
								player.membership = Boolean.parseBoolean(token2);
								break;
							case "canSpeak":
								player.canSpeak = Boolean.parseBoolean(token2);
								break;
							case "questPoints":
								player.questPoints = Integer.parseInt(token2);
								break;
							case "votePoints":
								player.votePoints = Integer.parseInt(token2);
								break;
							case "magic-book":
								player.playerMagicBook = Integer.parseInt(token2);
								break;
							case "special-amount":
								player.specAmount = Double.parseDouble(token2);
								break;
							case "selected-coffin":
								player.randomCoffin = Integer.parseInt(token2);
								break;
							case "isRunning":
								player.isRunning2 = Boolean.parseBoolean(token2);
								break;
							case "character-energy":
								player.playerEnergy = Integer.parseInt(token2);
								break;
							case "teleblock-length":
								player.teleBlockDelay = System.currentTimeMillis();
								player.teleBlockLength = Integer.parseInt(token2);
								break;
							case "lastYell":
								player.lastYell = Long.parseLong(token2);
								break;
							case "pc-points":
								player.pcPoints = Integer.parseInt(token2);
								break;
							case "magePoints":
								player.magePoints = Integer.parseInt(token2);
								break;
							case "autoRet":
								player.autoRet = Integer.parseInt(token2);
								break;
							case "flagged":
								player.accountFlagged = Boolean.parseBoolean(token2);
								break;
							case "lastLoginDate":
								player.lastLoginDate = Integer.parseInt(token2);
								break;
							case "hasBankpin":
								player.hasBankpin = Boolean.parseBoolean(token2);
								break;
							case "setPin":
								player.setPin = Boolean.parseBoolean(token2);
								break;
							case "pinRegisteredDeleteDay":
								player.pinDeleteDateRequested = Integer.parseInt(token2);
								break;
							case "requestPinDelete":
								player.requestPinDelete = Boolean.parseBoolean(token2);
								break;
							case "bankPin1":
								player.bankPin1 = Integer.parseInt(token2);
								break;
							case "bankPin2":
								player.bankPin2 = Integer.parseInt(token2);
								break;
							case "bankPin3":
								player.bankPin3 = Integer.parseInt(token2);
								break;
							case "bankPin4":
								player.bankPin4 = Integer.parseInt(token2);
								break;
							case "wave":
								player.waveId = Integer.parseInt(token2);
								break;
							case "ptjob":
								player.ptjob = Integer.parseInt(token2);
								break;
							case "creationAddress":
								player.creationAddress = token2;
								break;
							case "music":
								for (int j = 0; j < token3.length; j++) {
									player.getPlayList().unlocked[j] = Boolean.parseBoolean(token3[j]);
								}
								break;
							case "void":
								for (int j = 0; j < token3.length; j++) {
									player.voidStatus[j] = Integer.parseInt(token3[j]);
								}
								break;
							case "gwkc":
								player.killCount = Integer.parseInt(token2);
								break;
							case "fightMode":
								player.fightMode = Integer.parseInt(token2);
								break;
						}
						break;
					case 3:
						if (token.equals("character-equip")) {
							player.playerEquipment[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
							player.playerEquipmentN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
						}
						break;
					case 4:
						if (token.equals("character-look")) {
							player.playerAppearance[Integer.parseInt(token3[0])] = Integer	.parseInt(token3[1]);
						}
						break;
					case 5:
						if (token.equals("character-skill")) {
							player.playerLevel[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
							player.playerXP[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
						}
						break;
					case 6:
						if (token.equals("character-item")) {
							player.playerItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
							player.playerItemsN[Integer.parseInt(token3[0])] = Integer	.parseInt(token3[2]);
						}
						break;
					case 7:
						if (token.equals("character-bank")) {
							player.bankItems[Integer.parseInt(token3[0])] = Integer.parseInt(token3[1]);
							player.bankItemsN[Integer.parseInt(token3[0])] = Integer.parseInt(token3[2]);
						}
						break;
					case 8:
						if (token.equals("character-friend")) {
							player.friends[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
						}
						break;
					case 9:
						if (token.equals("character-ignore")) {
							player.ignores[Integer.parseInt(token3[0])] = Long.parseLong(token3[1]);
						}
						break;
				}
			} else {
				switch (line) {
					case "[ACCOUNT]":
						ReadMode = 1;
						break;
					case "[CHARACTER]":
						ReadMode = 2;
						break;
					case "[EQUIPMENT]":
						ReadMode = 3;
						break;
					case "[LOOK]":
						ReadMode = 4;
						break;
					case "[SKILLS]":
						ReadMode = 5;
						break;
					case "[ITEMS]":
						ReadMode = 6;
						break;
					case "[BANK]":
						ReadMode = 7;
						break;
					case "[FRIENDS]":
						ReadMode = 8;
						break;
					case "[IGNORES]":
						ReadMode = 9;
						break;
					case "[EOF]":
						try {
							characterfile.close();
						} catch (IOException ignored) {
						}
						return 1;
				}
			}
			try {
				line = characterfile.readLine();
			} catch (IOException ioexception1) {
				EndOfFile = true;
			}
		}
		try {
			characterfile.close();
		} catch (IOException ignored) {
		}
		if (doRealLogin)
			return 13;
		else
			return 14;
	}

	private static String passwordHash(String token2) {
	    String hashed = "HAS HAS FAILED!";
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] hash = digest.digest(token2.getBytes(StandardCharsets.UTF_8));

			hashed = Base64.getEncoder().encodeToString(hash);

			digest = MessageDigest.getInstance("SHA-256");
			hash = digest.digest(hashed.getBytes(StandardCharsets.UTF_8));

			hashed = Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashed;
	}

	/**
	 * Saving
	 **/
	public static boolean saveGame(Client player) {
		if (!player.saveFile || player.newPlayer || !player.saveCharacter) {
			// System.out.println("first");
			return false;
		}
		if (player.playerName == null
				|| PlayerHandler.players[player.playerId] == null) {
			// System.out.println("second");
			return false;
		}
		player.playerName = player.playerName2;
		int tbTime = (int) (player.teleBlockDelay - System.currentTimeMillis() + player.teleBlockLength);
		if (tbTime > 300000 || tbTime < 0) {
			tbTime = 0;
		}

		BufferedWriter characterfile = null;
		try {
			String filePath = System.getProperty("user.dir") + "/data/characters/" + player.playerName + ".txt";
			new File(filePath).getParentFile().mkdir();
			characterfile = new BufferedWriter(new FileWriter(filePath));

			/* ACCOUNT */
			characterfile.write("[ACCOUNT]", 0, 9);
			characterfile.newLine();
			characterfile.write("character-username = ", 0, 21);
			characterfile.write(player.playerName, 0,
					player.playerName.length());
			characterfile.newLine();
			if (player.playerRights == 0) {
				if (player.playerPass.length() < 40)
				{
					player.playerPass = passwordHash(player.playerPass);
				}
				characterfile.write("character-password = ", 0, 21);
				characterfile.write(player.playerPass, 0,
						player.playerPass.length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/* CHARACTER */
			characterfile.write("[CHARACTER]", 0, 11);
			characterfile.newLine();
			characterfile.write("character-height = ", 0, 19);
			characterfile.write(Integer.toString(player.heightLevel), 0,
					Integer.toString(player.heightLevel).length());
			characterfile.newLine();
			characterfile.write("character-posx = ", 0, 17);
			characterfile.write(Integer.toString(player.absX), 0, Integer
					.toString(player.absX).length());
			characterfile.newLine();
			characterfile.write("character-posy = ", 0, 17);
			characterfile.write(Integer.toString(player.absY), 0, Integer
					.toString(player.absY).length());
			characterfile.newLine();
			characterfile.write("character-rights = ", 0, 19);
			characterfile.write(Integer.toString(player.playerRights), 0,Integer.toString(player.playerRights).length());
			characterfile.newLine();
			characterfile.write("hasStarter = ", 0, 13);
			characterfile.write(Boolean.toString(player.hasStarter), 0, Boolean
					.toString(player.hasStarter).length());
			characterfile.newLine();
					characterfile.write("bankPin1 = ", 0, 11);
			characterfile.write(Integer.toString(player.bankPin1), 0, Integer
					.toString(player.bankPin1).length());
			characterfile.newLine();
			characterfile.write("bankPin2 = ", 0, 11);
			characterfile.write(Integer.toString(player.bankPin2), 0, Integer
					.toString(player.bankPin2).length());
			characterfile.newLine();
			characterfile.write("bankPin3 = ", 0, 11);
			characterfile.write(Integer.toString(player.bankPin3), 0, Integer
					.toString(player.bankPin3).length());
			characterfile.newLine();
			characterfile.write("bankPin4 = ", 0, 11);
			characterfile.write(Integer.toString(player.bankPin4), 0, Integer
					.toString(player.bankPin4).length());
			characterfile.newLine();
			characterfile.write("hasBankpin = ", 0, 13);
			characterfile.write(Boolean.toString(player.hasBankpin), 0, Boolean
					.toString(player.hasBankpin).length());
			characterfile.newLine();
			characterfile.write("pinRegisteredDeleteDay = ", 0, 25);
			characterfile.write(
					Integer.toString(player.pinDeleteDateRequested), 0, Integer
							.toString(player.pinDeleteDateRequested).length());
			characterfile.newLine();
			characterfile.write("requestPinDelete = ", 0, 19);
			characterfile.write(Boolean.toString(player.requestPinDelete), 0,
					Boolean.toString(player.requestPinDelete).length());
			characterfile.newLine();
			characterfile.write("lastLoginDate = ", 0, 16);
			characterfile.write(Integer.toString(player.lastLoginDate), 0,
					Integer.toString(player.lastLoginDate).length());
			characterfile.newLine();
				characterfile.write("setPin = ", 0, 9);
			characterfile.write(Boolean.toString(player.setPin), 0, Boolean
					.toString(player.setPin).length());
			characterfile.newLine();
			characterfile.write("hasPaid = ", 0, 10);
			characterfile.write(Boolean.toString(player.hasPaid), 0, Boolean
					.toString(player.hasPaid).length());
			characterfile.newLine();
			characterfile.write("lostCannon = ", 0, 13);
			characterfile.write(Boolean.toString(player.lostCannon), 0, Boolean.toString(player.lostCannon).length());
			characterfile.newLine();
			characterfile.write("cannonX = ", 0, 10);
			characterfile.write(Integer.toString(player.cannonX), 0, Integer.toString(player.cannonY).length());
			characterfile.newLine();
			characterfile.write("cannonY = ", 0, 10);
			characterfile.write(Integer.toString(player.cannonY), 0, Integer.toString(player.cannonY).length());
			characterfile.newLine();
			characterfile.write("myBalls = ", 0, 10);
			characterfile.write(Integer.toString(player.getCannon().myBalls), 0, Integer.toString(player.getCannon().myBalls).length());
			characterfile.newLine();
			characterfile.write("poison = ", 0, 9);
			characterfile.write(Boolean.toString(player.poison), 0, Boolean
					.toString(player.poison).length());
			characterfile.newLine();
			characterfile.write("spiritTree = ", 0, 13);
			characterfile.write(Boolean.toString(player.spiritTree), 0, Boolean
					.toString(player.spiritTree).length());
			characterfile.newLine();
			characterfile.write("npcCanAttack = ", 0, 15);
			characterfile.write(Boolean.toString(player.npcCanAttack), 0, Boolean
					.toString(player.npcCanAttack).length());
			characterfile.newLine();
			characterfile.write("rope = ", 0, 7);
			characterfile.write(Boolean.toString(player.rope), 0, Boolean
					.toString(player.rope).length());
			characterfile.newLine();
			characterfile.write("rope2 = ", 0, 8);
			characterfile.write(Boolean.toString(player.rope2), 0, Boolean
					.toString(player.rope2).length());
			characterfile.newLine();
			characterfile.write("recievedMask = ", 0, 15);
			characterfile.write(Boolean.toString(player.recievedMask), 0, Boolean
					.toString(player.recievedMask).length());
			characterfile.newLine();
			characterfile.write("recievedReward = ", 0, 17);
			characterfile.write(Boolean.toString(player.recievedReward), 0, Boolean
					.toString(player.recievedReward).length());
			characterfile.newLine();
			characterfile.write("isBotting = ", 0, 12);
			characterfile.write(Boolean.toString(player.isBotting), 0,
					Boolean.toString(player.isBotting).length());
			characterfile.newLine();
			characterfile.write("global-damage = ", 0, 16);
			characterfile.write(Integer.toString(player.globalDamageDealt), 0, Integer
					.toString(player.globalDamageDealt).length());
			characterfile.newLine();
			characterfile.write("brightness = ", 0, 13);
			characterfile.write(Integer.toString(player.brightness), 0, Integer
					.toString(player.brightness).length());
			characterfile.newLine();
			characterfile.write("closeTutorialInterface = ", 0, 25);
			characterfile.write(
					Boolean.toString(player.closeTutorialInterface), 0, Boolean
							.toString(player.closeTutorialInterface).length());
			characterfile.newLine();
			characterfile.write("canWalkTutorial = ", 0, 18);
			characterfile.write(Boolean.toString(player.canWalkTutorial), 0,
					Boolean.toString(player.canWalkTutorial).length());
			characterfile.newLine();
			characterfile.write("village = ", 0, 10);
			characterfile.write(Boolean.toString(player.village), 0, Boolean
					.toString(player.village).length());
			characterfile.newLine();
			characterfile.write("lastThieve = ", 0, 13);
			characterfile.write(Long.toString(player.lastThieve), 0, Long.toString(player.lastThieve).length());
			characterfile.newLine();
			characterfile.write("homeTele = ", 0, 11);
			characterfile.write(Long.toString(player.homeTele), 0, Long.toString(player.homeTele).length());
			characterfile.newLine();
			characterfile.write("strongHold = ", 0, 13);
			characterfile.write(Boolean.toString(player.strongHold), 0, Boolean
					.toString(player.strongHold).length());
			characterfile.newLine();
			characterfile.write("character-energy = ", 0, 19);
			characterfile.write(Integer.toString((int) Math.ceil(player.playerEnergy)), 0,
					Integer.toString((int) Math.ceil(player.playerEnergy)).length());
			characterfile.newLine();
			characterfile.write("crystal-bow-shots = ", 0, 20);
			characterfile.write(Integer.toString(player.crystalBowArrowCount),
					0, Integer.toString(player.crystalBowArrowCount).length());
			characterfile.newLine();
			characterfile.write("splitChat = ", 0, 12);
			characterfile.write(Boolean.toString(player.splitChat), 0, Boolean
					.toString(player.splitChat).length());
			characterfile.newLine();
			characterfile.write("canSpeak = ", 0, 11);
			characterfile.write(Boolean.toString(player.canSpeak), 0, Boolean
					.toString(player.canSpeak).length());
			characterfile.newLine();
			for (int b = 0; b < player.barrowsNpcs.length; b++) {
				characterfile.write("barrowsNpcs = ", 0, 14);
				characterfile.write(Integer.toString(b), 0, Integer.toString(b).length());
				characterfile.write("	", 0, 1);
				characterfile.write(player.barrowsNpcs[b][1] <= 1 ? Integer.toString(0) : Integer.toString(player.barrowsNpcs[b][1]), 0, Integer.toString(player.barrowsNpcs[b][1]).length());
				characterfile.newLine();
			}
			characterfile.write("questStages = ", 0, 14);
			characterfile.write(Integer.toString(player.questStages), 0,
					Integer.toString(player.questStages).length());
			characterfile.newLine();
			characterfile.write("SlayerMaster = ", 0, 15);
			characterfile.write(Integer.toString(player.SlayerMaster), 0,
					Integer.toString(player.SlayerMaster).length());
			characterfile.newLine();
			characterfile.write("music = ", 0, 8);
			String music = "";
			for (boolean element : player.getPlayList().unlocked) {
				music += element + "\t";
			}
			characterfile.write(music);
			characterfile.newLine();
			characterfile.write("randomActions = ", 0, 16);
			characterfile.write(Integer.toString(player.randomActions), 0,
					Integer.toString(player.randomActions).length());
			characterfile.newLine();
			characterfile.write("blackMarks = ", 0, 13);
			characterfile.write(Integer.toString(player.blackMarks), 0, Integer
					.toString(player.blackMarks).length());
			characterfile.newLine();
			characterfile.write("tutorial-progress = ", 0, 20);
			characterfile.write(Integer.toString(player.tutorialProgress), 0,
					Integer.toString(player.tutorialProgress).length());
			characterfile.newLine();
			characterfile.write("skull-timer = ", 0, 14);
			characterfile.write(Integer.toString(player.skullTimer), 0, Integer
					.toString(player.skullTimer).length());
			characterfile.newLine();
			characterfile.write("recoilHits = ", 0, 13);
			characterfile.write(Integer.toString(player.recoilHits), 0, Integer
					.toString(player.recoilHits).length());
			characterfile.newLine();
			characterfile.write("lastX = ", 0, 8);
			characterfile.write(Integer.toString(player.lastX), 0, Integer
					.toString(player.lastX).length());
			characterfile.newLine();
			characterfile.write("lastY = ", 0, 8);
			characterfile.write(Integer.toString(player.lastY), 0, Integer
					.toString(player.lastY).length());
			characterfile.newLine();
			characterfile.write("lastH = ", 0, 8);
			characterfile.write(Integer.toString(player.lastH), 0, Integer
					.toString(player.lastH).length());
			characterfile.newLine();
			for (int i = 0; i < player.removedTasks.length; i++) {
				characterfile.write("removedTask" + i + " = ", 0, 15);
				characterfile.write(Integer.toString(player.removedTasks[i]),
						0, Integer.toString(player.removedTasks[i]).length());
				characterfile.newLine();
			}
			characterfile.write("creationAddress = ", 0, 18);
			characterfile.write(player.creationAddress, 0,
					player.creationAddress.length());
			characterfile.newLine();
			characterfile.write("has-npc = ", 0, 10);
			characterfile.write(Boolean.toString(player.hasNpc), 0, Boolean
					.toString(player.hasNpc).length());
			characterfile.newLine();
			characterfile.write("summonId = ", 0, 11);
			characterfile.write(Integer.toString(player.summonId), 0, Integer
					.toString(player.summonId).length());
			characterfile.newLine();
			characterfile.write("thankedForDonation = ", 0, 21);
			characterfile.write(Integer.toString(player.thankedForDonation), 0,
					Integer.toString(player.thankedForDonation).length());
			characterfile.newLine();
			characterfile.write("membership = ", 0, 13);
			characterfile.write(Boolean.toString(player.membership), 0, Boolean
					.toString(player.membership).length());
			characterfile.newLine();
			characterfile.write("questPoints = ", 0, 14);
			characterfile.write(Integer.toString(player.questPoints), 0,
					Integer.toString(player.questPoints).length());
			characterfile.newLine();
			characterfile.write("votePoints = ", 0, 13);
			characterfile.write(Integer.toString(player.votePoints), 0,
					Integer.toString(player.votePoints).length());
			characterfile.newLine();
			characterfile.write("bananas = ", 0, 10);
			characterfile.write(Integer.toString(player.bananas), 0, Integer
					.toString(player.bananas).length());
			characterfile.newLine();
			characterfile.write("magic-book = ", 0, 13);
			characterfile.write(Integer.toString(player.playerMagicBook), 0,
					Integer.toString(player.playerMagicBook).length());
			characterfile.newLine();
			characterfile.write("special-amount = ", 0, 17);
			characterfile.write(Double.toString(player.specAmount), 0, Double
					.toString(player.specAmount).length());
			characterfile.newLine();
			characterfile.write("musicOn = ", 0, 10);
			characterfile.write(Boolean.toString(player.musicOn), 0, Boolean
					.toString(player.musicOn).length());
			characterfile.newLine();
			characterfile.write("needsNewTask = ", 0, 15);
			characterfile.write(Boolean.toString(player.needsNewTask), 0,
					Boolean.toString(player.needsNewTask).length());
			characterfile.newLine();
			characterfile.write("luthas = ", 0, 9);
			characterfile.write(Boolean.toString(player.luthas), 0, Boolean
					.toString(player.luthas).length());
			characterfile.newLine();
			characterfile.write("selected-coffin = ", 0, 18);
			characterfile.write(Integer.toString(player.randomCoffin), 0,
					Integer.toString(player.randomCoffin).length());
			characterfile.newLine();
			characterfile.write("runeMist = ", 0, 11);
			characterfile.write(Integer.toString(player.runeMist), 0, Integer
					.toString(player.runeMist).length());
			characterfile.newLine();
			characterfile.write("blackKnight = ", 0, 14);
			characterfile.write(Integer.toString(player.blackKnight), 0, Integer
					.toString(player.blackKnight).length());
			characterfile.newLine();
			characterfile.write("shieldArrav = ", 0, 14);
			characterfile.write(Integer.toString(player.shieldArrav), 0, Integer
					.toString(player.shieldArrav).length());
			characterfile.newLine();
			characterfile.write("cookAss = ", 0, 10);
			characterfile.write(Integer.toString(player.cookAss), 0, Integer
					.toString(player.cookAss).length());
			characterfile.newLine();
			characterfile.write("pirateTreasure = ", 0, 17);
			characterfile.write(Integer.toString(player.pirateTreasure), 0,
					Integer.toString(player.pirateTreasure).length());
			characterfile.newLine();
			characterfile.write("ptjob = ", 0, 8);
			characterfile.write(Integer.toString(player.ptjob), 0, Integer
					.toString(player.ptjob).length());
			characterfile.newLine();
			characterfile.write("doricQuest = ", 0, 13);
			characterfile.write(Integer.toString(player.doricQuest), 0, Integer
					.toString(player.doricQuest).length());
			characterfile.newLine();
			characterfile.write("dragonSlayerQuestStage = ", 0, 25);
			characterfile.write(
					Integer.toString(player.dragonSlayerQuestStage), 0, Integer
							.toString(player.dragonSlayerQuestStage).length());
			characterfile.newLine();
			characterfile.write("impsC = ", 0, 8);
			characterfile.write(Integer.toString(player.impsC), 0, Integer
					.toString(player.impsC).length());
			characterfile.newLine();
			characterfile.write("knightS = ", 0, 10);
			characterfile.write(Integer.toString(player.knightS), 0, Integer.toString(player.knightS).length());
			characterfile.newLine();
			characterfile.write("sheepShear = ", 0, 13);
			characterfile.write(Integer.toString(player.sheepShear), 0, Integer
					.toString(player.sheepShear).length());
			characterfile.newLine();
			characterfile.write("romeo-juliet = ", 0, 15);
			characterfile.write(Integer.toString(player.romeojuliet), 0,
					Integer.toString(player.romeojuliet).length());
			characterfile.newLine();
			characterfile.write("gertCat = ", 0, 10);
			characterfile.write(Integer.toString(player.gertCat), 0, Integer
					.toString(player.gertCat).length());
			characterfile.newLine();
			characterfile.write("cw-games = ", 0, 11);
			characterfile.write(Integer.toString(player.cwGames), 0, Integer
					.toString(player.cwGames).length());
			characterfile.newLine();
			characterfile.write("witchspot = ", 0, 12);
			characterfile.write(Integer.toString(player.witchspot), 0, Integer
					.toString(player.witchspot).length());
			characterfile.newLine();
			characterfile.write("restGhost = ", 0, 12);
			characterfile.write(Integer.toString(player.restGhost), 0, Integer
					.toString(player.restGhost).length());
			characterfile.newLine();
			characterfile.write("vampSlayer = ", 0, 13);
			characterfile.write(Integer.toString(player.vampSlayer), 0, Integer
					.toString(player.vampSlayer).length());
			characterfile.newLine();
			characterfile.write("RatDied2 = ", 0, 11);
			characterfile.write(Boolean.toString(player.ratdied2), 0, Boolean
					.toString(player.ratdied2).length());
			characterfile.newLine();
			characterfile.write("debugMode = ", 0, 12);
			characterfile.write(Boolean.toString(player.debugMode), 0, Boolean
					.toString(player.debugMode).length());
			characterfile.newLine();
			characterfile.write("randomToggle = ", 0, 15);
			characterfile.write(Boolean.toString(player.randomEventsEnabled), 0, Boolean
					.toString(player.randomEventsEnabled).length());
			characterfile.newLine();
			characterfile.write("teleblock-length = ", 0, 19);
			characterfile.write(Integer.toString(tbTime), 0,
					Integer.toString(tbTime).length());
			characterfile.newLine();
			characterfile.write("pc-points = ", 0, 12);
			characterfile.write(Integer.toString(player.pcPoints), 0, Integer
					.toString(player.pcPoints).length());
			characterfile.newLine();
			characterfile.write("lastYell = ", 0, 11);
			characterfile.write(Long.toString(player.lastYell), 0, Long.toString(player.lastYell).length());
			characterfile.newLine();
			characterfile.write("slayerTask = ", 0, 13);
			characterfile.write(Integer.toString(player.slayerTask), 0, Integer
					.toString(player.slayerTask).length());
			characterfile.newLine();
			characterfile.write("taskAmount = ", 0, 13);
			characterfile.write(Integer.toString(player.taskAmount), 0, Integer
					.toString(player.taskAmount).length());
			characterfile.newLine();
			characterfile.write("magePoints = ", 0, 13);
			characterfile.write(Integer.toString(player.magePoints), 0, Integer
					.toString(player.magePoints).length());
			characterfile.newLine();
			characterfile.write("autoRet = ", 0, 10);
			characterfile.write(Integer.toString(player.autoRet), 0, Integer
					.toString(player.autoRet).length());
			characterfile.newLine();
			characterfile.write("barrowsKillCount = ", 0, 19);
			characterfile.write(Integer.toString(player.barrowsKillCount), 0,
					Integer.toString(player.barrowsKillCount).length());
			characterfile.newLine();
			characterfile.write("slayerPoints = ", 0, 15);
			characterfile.write(Integer.toString(player.slayerPoints), 0,
					Integer.toString(player.slayerPoints).length());
			characterfile.newLine();
			characterfile.write("flagged = ", 0, 10);
			characterfile.write(Boolean.toString(player.accountFlagged), 0,
					Boolean.toString(player.accountFlagged).length());
			characterfile.newLine();
			characterfile.write("wave = ", 0, 7);
			characterfile.write(Integer.toString(player.waveId), 0, Integer
					.toString(player.waveId).length());
			characterfile.newLine();
			characterfile.write("gwkc = ", 0, 7);
			characterfile.write(Integer.toString(player.killCount), 0, Integer
					.toString(player.killCount).length());
			characterfile.newLine();
			characterfile.write("isRunning = ", 0, 12);
			characterfile.write(Boolean.toString(player.isRunning2), 0, Boolean
					.toString(player.isRunning2).length());
			characterfile.newLine();
			characterfile.write("fightMode = ", 0, 12);
			characterfile.write(Integer.toString(player.fightMode), 0, Integer
					.toString(player.fightMode).length());
			characterfile.newLine();
			characterfile.write("void = ", 0, 7);
			String toWrite = player.voidStatus[0] + "\t" + player.voidStatus[1]
					+ "\t" + player.voidStatus[2] + "\t" + player.voidStatus[3]
					+ "\t" + player.voidStatus[4];
			characterfile.write(toWrite);
			characterfile.newLine();
			characterfile.newLine();

			/* EQUIPMENT */
			characterfile.write("[EQUIPMENT]", 0, 11);
			characterfile.newLine();
			for (int i = 0; i < player.playerEquipment.length; i++) {
				characterfile.write("character-equip = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i)
						.length());
				characterfile.write("	", 0, 1);
				characterfile.write(
						Integer.toString(player.playerEquipment[i]), 0, Integer
								.toString(player.playerEquipment[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(
						Integer.toString(player.playerEquipmentN[i]), 0,
						Integer.toString(player.playerEquipmentN[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.newLine();
			}
			characterfile.newLine();

			/* LOOK */
			characterfile.write("[LOOK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < player.playerAppearance.length; i++) {
				characterfile.write("character-look = ", 0, 17);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i)
						.length());
				characterfile.write("	", 0, 1);
				characterfile.write(
						Integer.toString(player.playerAppearance[i]), 0,
						Integer.toString(player.playerAppearance[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/* SKILLS */
			characterfile.write("[SKILLS]", 0, 8);
			characterfile.newLine();
			for (int i = 0; i < player.playerLevel.length; i++) {
				characterfile.write("character-skill = ", 0, 18);
				characterfile.write(Integer.toString(i), 0, Integer.toString(i)
						.length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(player.playerLevel[i]), 0,
						Integer.toString(player.playerLevel[i]).length());
				characterfile.write("	", 0, 1);
				characterfile.write(Integer.toString(player.playerXP[i]), 0,
						Integer.toString(player.playerXP[i]).length());
				characterfile.newLine();
			}
			characterfile.newLine();

			/* ITEMS */
			characterfile.write("[ITEMS]", 0, 7);
			characterfile.newLine();
			for (int i = 0; i < player.playerItems.length; i++) {
				if (player.playerItems[i] > 0) {
					characterfile.write("character-item = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer
							.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(
							Integer.toString(player.playerItems[i]), 0, Integer
									.toString(player.playerItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(
							Integer.toString(player.playerItemsN[i]), 0,
							Integer.toString(player.playerItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* BANK */
			characterfile.write("[BANK]", 0, 6);
			characterfile.newLine();
			for (int i = 0; i < player.bankItems.length; i++) {
				if (player.bankItems[i] > 0) {
					characterfile.write("character-bank = ", 0, 17);
					characterfile.write(Integer.toString(i), 0, Integer
							.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(player.bankItems[i]),
							0, Integer.toString(player.bankItems[i]).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Integer.toString(player.bankItemsN[i]),
							0, Integer.toString(player.bankItemsN[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			/* FRIENDS */
			characterfile.write("[FRIENDS]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < player.friends.length; i++) {
				if (player.friends[i] > 0) {
					characterfile.write("character-friend = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer
							.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write("" + player.friends[i]);
					characterfile.newLine();
				}
			}
			characterfile.newLine();

			characterfile.write("[IGNORES]", 0, 9);
			characterfile.newLine();
			for (int i = 0; i < player.ignores.length; i++) {
				if (player.ignores[i] > 0) {
					characterfile.write("character-ignore = ", 0, 19);
					characterfile.write(Integer.toString(i), 0, Integer.toString(i).length());
					characterfile.write("	", 0, 1);
					characterfile.write(Long.toString(player.ignores[i]), 0, Long.toString(player.ignores[i]).length());
					characterfile.newLine();
				}
			}
			characterfile.newLine();
			
			/* EOF */
			characterfile.write("[EOF]", 0, 5);
			characterfile.newLine();
			characterfile.newLine();
			characterfile.close();
		} catch (IOException ioexception) {
			Misc.println(player.playerName + ": error writing file.");
			return false;
		}
		return true;
	}

}
