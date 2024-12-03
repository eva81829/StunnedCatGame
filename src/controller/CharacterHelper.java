/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import gameobject.PhysicalGameObject;
import gameobject.PhysicalGameObject.*;
import java.awt.image.BufferedImage;
import gameobjectcharacter.Character;
import java.awt.Graphics;
import utils.Global;
import utils.ImagePath;

/**
 *
 *
 * @author asdfg
 */
public class CharacterHelper {

    private static final int[] CAT_ACT = {0, 1, 2, 3, 1};
    private static final int[] CAT_ACTDIV = {3, 2, 1, 0, 2};
    private static final int[] CAT_STUN = {0, 0, 0, 0, 0};
    private static final int[] CAT_JUMP = {0, 1, 2, 3, 4};
    private static final int[] CAT_JUMPDIV = {4, 3, 2, 1, 0};
    private static final int[] BOY_ACT = {0, 1, 2, 3, 4, 5};
    private static final int[] BOY_ACTDIV = {5, 4, 3, 2, 1, 0};
    private static final int[] BOY_JUMP = {1, 3, 4, 6, 8, 9};
    private static final int[] BOY_JUMPDIV = {8, 6, 5, 3, 1, 0};
    private static final int[] BOY_STUN = {0, 0, 0, 0, 0, 0};
    private static int[] actExcute;
    private BufferedImage img;

    public CharacterHelper(Character character) {
        img = getCharacter(character);
    }

    private BufferedImage getCharacter(Character character) {
        if (character.getCharacterState() instanceof Character.StateCat) {
            ImageResourceController irc = ImageResourceController.getInstance();
            if (character.getStateDirection() instanceof PhysicalGameObject.StateFaceRight) {
                if (character.getStateWeightlessness() instanceof PhysicalGameObject.StateOnFloor) {
                    if (character.getStateEmotion() instanceof Character.StateNormal) {
                        actExcute = CAT_ACT;
                        return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Cat.SMALL_CAT_ACT));
                    } else if (character.getStateEmotion() instanceof Character.StateShock) {
                        actExcute = CAT_STUN;
                        return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Cat.SMALL_CAT_STUN));
                    }
                } else if (character.getStateWeightlessness() instanceof PhysicalGameObject.StateInAir) {
                    actExcute = CAT_JUMP;
                    return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Cat.SMALL_CAT_JUMP));
                }
            } else if (character.getStateDirection() instanceof PhysicalGameObject.StateFaceLeft) {
                if (character.getStateWeightlessness() instanceof PhysicalGameObject.StateOnFloor) {
                    if (character.getStateEmotion() instanceof Character.StateNormal) {
                        actExcute = CAT_ACTDIV;
                        return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Cat.SMALL_CAT_ACTDIV));
                    } else if (character.getStateEmotion() instanceof Character.StateShock) {
                        actExcute = CAT_STUN;
                        return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Cat.SMALL_CAT_STUNDIV));
                    }
                } else if (character.getStateWeightlessness() instanceof PhysicalGameObject.StateInAir) {
                    actExcute = CAT_JUMPDIV;
                    return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Cat.SMALL_CAT_JUMPDIV));
                }
            }
        } else if (character.getCharacterState() instanceof Character.StateBoy) {
            ImageResourceController irc = ImageResourceController.getInstance();
            if (character.getStateDirection() instanceof PhysicalGameObject.StateFaceRight) {
                if (character.getStateWeightlessness() instanceof PhysicalGameObject.StateOnFloor) {
                    if (character.getStateEmotion() instanceof Character.StateNormal) {
                        actExcute = BOY_ACT;
                        return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Boy.BOY_ACT));
                    } else if (character.getStateEmotion() instanceof Character.StateShock) {
                        actExcute = BOY_STUN;
                        return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Boy.BOY_STUN));
                    }
                } else if (character.getStateWeightlessness() instanceof PhysicalGameObject.StateInAir) {
                    actExcute = BOY_JUMP;
                    return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Boy.BOY_JUMP));
                }
            } else if (character.getStateDirection() instanceof PhysicalGameObject.StateFaceLeft) {
                if (character.getStateWeightlessness() instanceof PhysicalGameObject.StateOnFloor) {
                    if (character.getStateEmotion() instanceof Character.StateNormal) {
                        actExcute = BOY_ACTDIV;
                        return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Boy.BOY_ACTDIV));
                    } else if (character.getStateEmotion() instanceof Character.StateShock) {
                        actExcute = BOY_STUN;
                        return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Boy.BOY_STUNDIV));
                    }
                } else if (character.getStateWeightlessness() instanceof PhysicalGameObject.StateInAir) {
                    actExcute = BOY_JUMPDIV;
                    return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Boy.BOY_JUMPDIV));
                }
            }
//            ImageResourceController irc = ImageResourceController.getInstance();
//            if (character.getStateDirection() instanceof PhysicalGameObject.StateFaceRight) {
//                actExcute = BOY_ACT;
//                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Boy.BOY_ACT));
//            } else if (character.getStateDirection() instanceof PhysicalGameObject.StateFaceLeft) {
//                actExcute = BOY_ACTDIV;
//                return irc.tryGetImage(PathBuilder.getImage(ImagePath.Character.Boy.BOY_ACTDIV));
//            }
        }
        return null;
    }

    public int[] getActExcute() {
        return actExcute;
    }

    public void paint(Graphics g, int x, int y, int width, int height, int act) {
        if (img == null) {
            return;
        }
        g.drawImage(img, x, y, x + width, y + height,
                0 + actExcute[act] * Global.CHARACTER_X_OFFSET, 0,
                140 + actExcute[act] * Global.CHARACTER_X_OFFSET, 140, null);
    }
}
