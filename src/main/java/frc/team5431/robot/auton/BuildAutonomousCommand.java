package frc.team5431.robot.auton;

import java.util.Queue;

import frc.team5431.robot.Robot;
import frc.team5431.robot.Titan;
import frc.team5431.robot.Robot.AutonPriority;
import frc.team5431.robot.Titan.GameData.FieldObject;
import frc.team5431.robot.Titan.GameData.SideChooser;
import frc.team5431.robot.Robot.AutonPosition;

public class BuildAutonomousCommand extends Titan.Command<Robot> {
	private final AutonPriority priority;
	private final AutonPosition position;
	
	public BuildAutonomousCommand(final AutonPriority pri, final AutonPosition pos) {
		name = "BuildAutonomousStep ";
		properties = "Build the autonomous based off of the game data";
		
		priority = pri;
		position = pos;
	}

	@Override
	public CommandResult periodic(final Robot robot) {
		return CommandResult.COMPLETE;
	}

	@Override
	public void init(final Robot robot) {
		robot.getGameData().init(); //Capture the current game data
	}

	@Override
	public void done(final Robot robot) {
		final Titan.GameData game = robot.getGameData();
		final Titan.CommandQueue<Robot> aSteps = robot.getAutonSteps();
		switch(position) {
		case CENTER: {
				switch(priority) {
				case SWITCH: {
						game.setSelectedObject(FieldObject.SWITCH);
						//aSteps.add(new DriveStep(60.0));
						game.runSide(new SideChooser() {
							@Override
							public void left() {
								aSteps.add(new DriveCommand(-46.5, -65.0));
								aSteps.add(new DriveCommand(-51, 78));
								aSteps.add(new WaitCommand(100));
							}

							@Override
							public void right() {
								//aSteps.add(new DriveStep(3.0));
								//aSteps.add(new TurnStep(35.0)); 
								aSteps.add(new DriveCommand(-60.0, 30.0));
								aSteps.add(new DriveCommand(-32.0, -70.0));
								//aSteps.add(new TurnStep(-35.0)); 
								//aSteps.add(new DriveStep(10.0));
							}
						});
						aSteps.add(new SwitchCubeCommand());
					}
					break;
				}
			}
			break;
		case RIGHT: {
				switch(priority) { // TODO ADD A MIDDLE CUBE BLOCK THAT TURNS AND SHOOTS A BLOCK
				case SWITCH_SCALE:
				case SWITCH: {
						game.setSelectedObject(FieldObject.SWITCH);
						//aSteps.add(new DriveStep(60.0));
						game.runSide(new SideChooser() {
							@Override
							public void left() {
								//aSteps.add(new DriveStep(46.5, -65.0));
								//aSteps.add(new DriveStep(54.5, 78));
							}
	
							@Override
							public void right() {
								aSteps.add(new DriveCommand(-88.0, 25.0));
								if(priority == AutonPriority.SWITCH_SCALE) {
									aSteps.add(new DriveCommand(2));
									aSteps.add(new TurnCommand(180));
									aSteps.add(new DriveCommand(15));
								}
							}
						});
					}
					break;
				case SCALE: {
						game.setSelectedObject(FieldObject.SCALE);
						game.runSide(new SideChooser() {
							@Override
							public void left() {
								//aSteps.add
								
							}

							@Override
							public void right() {
								aSteps.add(new DriveCommand(-265.0));
								aSteps.add(new WaitCommand(-250));
								aSteps.add(new TurnCommand(60.0));
							}							
						});
					}
					break;
				}
			}
			break;
		}
	}
}
