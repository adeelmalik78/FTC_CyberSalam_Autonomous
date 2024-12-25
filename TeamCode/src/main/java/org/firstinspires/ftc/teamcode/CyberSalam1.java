package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "CyberSalam - Autonomous")

public class CyberSalam1 extends LinearOpMode {
    private Gyroscope imu;
    private DcMotor armbottom;
    private DcMotor armtop;
    private DcMotor intake;
    private DcMotor moterLB;
    private DcMotor motorLF;
    private DcMotor motorRB;
    private DcMotor motorRF;
    private Servo servoclaw;

    double fPower;
    int armbottomposition;
    int fposition;

    @Override
    public void runOpMode() {
        // INIT code here
        armbottom = hardwareMap.get(DcMotor.class, "armbottom");
        armtop = hardwareMap.get(DcMotor.class, "armtop");
        intake = hardwareMap.get(DcMotor.class, "intake");
        moterLB = hardwareMap.get(DcMotor.class, "moterLB");
        motorLF = hardwareMap.get(DcMotor.class, "motorLF");
        motorRB = hardwareMap.get(DcMotor.class, "motorRB");
        motorRF = hardwareMap.get(DcMotor.class, "motorRF");
        servoclaw = hardwareMap.get(Servo.class, "servoclaw");

        armbottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armtop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        moterLB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        INITIALIZE_MOTORS();
        CLAW_CLOSE();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        if (opModeIsActive()) {
            // Put run blocks here.
            // Phase 1: Raise arm, move forward and hang 1st specimen
            while (opModeIsActive()) {
                fPower = 0.8;

                armbottomposition = 2800;
                ARM_BOTTOM();

                fposition = 1025;
                MOVE_FORWARD_OR_BACKWARDS();

                armbottomposition = -1000;
                ARM_BOTTOM();

                CLAW_OPEN();

                fposition = -200;
                MOVE_FORWARD_OR_BACKWARDS();

                armbottomposition = -1800;
                ARM_BOTTOM();

                break;
            }

            // Phase 2: Retrieve first sample and bring to the observation zone
            while (opModeIsActive()) {
                fPower = 0.8;

                fposition = -1700;
                SHIFT_SIDEWAYS();

                fposition = 1750;
                MOVE_FORWARD_OR_BACKWARDS();

                fposition = -600;
                SHIFT_SIDEWAYS();

                fposition = -2200;
                MOVE_FORWARD_OR_BACKWARDS();

                fposition = 300;
                MOVE_FORWARD_OR_BACKWARDS();

                break;
            }

            // Phase 3: Reposition robot to grab sample
            while (opModeIsActive()) {
                fPower = 0.8;

                fposition = 2250;
                TURN_RIGHT();

                armbottomposition = 1100;
                ARM_BOTTOM();

                break;
            }

            // Phase 4: Grab 2nd specimen and hang it
            while (opModeIsActive()) {
                fPower = 0.8;

                fposition = 300;
                MOVE_FORWARD_OR_BACKWARDS();

                CLAW_CLOSE();

                armbottomposition = 1700;
                ARM_BOTTOM();

                fposition = -300;
                MOVE_FORWARD_OR_BACKWARDS();

                fposition = 2100;
                TURN_RIGHT();

                fposition = 2100;
                SHIFT_SIDEWAYS();

                fposition = 300;
                MOVE_FORWARD_OR_BACKWARDS();

                armbottomposition = -1000;
                ARM_BOTTOM();

                CLAW_OPEN();

                fposition = -300;
                MOVE_FORWARD_OR_BACKWARDS();

                break;
            }

            // Phase 5: Go back to the observation zone and grab 3rd specimen and hang it.
            while (opModeIsActive()) {
                fPower = 0.8;

                fposition = -2100;
                SHIFT_SIDEWAYS();

                fposition = 2100;
                TURN_RIGHT();

                armbottomposition = -400;
                ARM_BOTTOM();

                fposition = 100;
                MOVE_FORWARD_OR_BACKWARDS();

                CLAW_CLOSE();

                fposition = -300;
                MOVE_FORWARD_OR_BACKWARDS();

                break;
            }
        }
    }

    /**
     * Describe this function...
     */
    private void RUN_TO_POSITION() {
        moterLB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (moterLB.isBusy()) {
            sleep(10);
        }
    }

    /**
     * Encoder, and brake
     */
    private void INITIALIZE_MOTORS() {
        armbottom.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armtop.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        moterLB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    /**
     * Telemetry For all motors
     */
    private void TELEMETRY() {
        telemetry.addData("armbottom", armbottom.getCurrentPosition());
        telemetry.addData("armtop", armtop.getCurrentPosition());
        telemetry.addData("intake", intake.getCurrentPosition());
        telemetry.addData("motorLB", moterLB.getCurrentPosition());
        telemetry.addData("motorLF", motorLF.getCurrentPosition());
        telemetry.addData("motorRB", motorRB.getCurrentPosition());
        telemetry.addData("motorRF", motorRF.getCurrentPosition());
    }

    /**
     * Target position for the motors
     */
    private void MOVE_FORWARD_OR_BACKWARDS() {
        INITIALIZE_MOTORS();
        moterLB.setPower(-fPower);
        motorLF.setPower(-fPower);
        motorRB.setPower(fPower);
        motorRF.setPower(fPower);
        moterLB.setTargetPosition(-fposition);
        motorLF.setTargetPosition(-fposition);
        motorRB.setTargetPosition(fposition);
        motorRF.setTargetPosition(fposition);
        RUN_TO_POSITION();
        TELEMETRY();
        telemetry.update();
    }

    /**
     * Describe this function...
     */
    private void SHIFT_SIDEWAYS() {
        INITIALIZE_MOTORS();
        moterLB.setPower(-fPower);
        motorLF.setPower(fPower);
        motorRB.setPower(-fPower);
        motorRF.setPower(fPower);
        moterLB.setTargetPosition(-fposition);
        motorLF.setTargetPosition(fposition);
        motorRB.setTargetPosition(-fposition);
        motorRF.setTargetPosition(fposition);
        RUN_TO_POSITION();
        TELEMETRY();
        telemetry.update();
    }

    /**
     * Describe this function...
     */
    private void ARM_BOTTOM() {
        INITIALIZE_MOTORS();
        armbottom.setPower(-(4 * fPower));
        armbottom.setTargetPosition(armbottomposition);
        armbottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (armbottom.isBusy()) {
            sleep(10);
        }
        telemetry.update();
    }

    /**
     * Describe this function...
     */
    private void TURN_LEFT() {
        INITIALIZE_MOTORS();
        moterLB.setPower(fPower);
        motorLF.setPower(fPower);
        motorRB.setPower(fPower);
        motorRF.setPower(fPower);
        moterLB.setTargetPosition(fposition);
        motorLF.setTargetPosition(fposition);
        motorRB.setTargetPosition(fposition);
        motorRF.setTargetPosition(fposition);
        RUN_TO_POSITION();
        telemetry.update();
    }

    /**
     * Describe this function...
     */
    private void CLAW_CLOSE() {
        servoclaw.setDirection(Servo.Direction.REVERSE);
        servoclaw.setPosition(0);
    }

    /**
     * Describe this function...
     */
    private void CLAW_OPEN() {
        servoclaw.setDirection(Servo.Direction.REVERSE);
        servoclaw.setPosition(0.15);
    }

    /**
     * Describe this function...
     */
    private void TURN_RIGHT() {
        moterLB.setPower(fPower);
        motorLF.setPower(fPower);
        motorRB.setPower(fPower);
        motorRF.setPower(fPower);
        moterLB.setTargetPosition(-fposition);
        motorLF.setTargetPosition(-fposition);
        motorRB.setTargetPosition(-fposition);
        motorRF.setTargetPosition(-fposition);
        RUN_TO_POSITION();
        telemetry.update();
    }
}
