package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Joysticks Control BETA", group="Iterative Opmode")

public class joystick_final extends OpMode {

    // BACKUP
    private ElapsedTime timp = new ElapsedTime();

    // MOTOARE
    private DcMotor   left_DriveOne = null;
    private DcMotor   left_DriveTwo = null;
    private DcMotor   right_DriveOne = null;
    private DcMotor   right_DriveTwo = null;

    private Servo     servo_autos = null;
    private Servo     servo_tavaOne = null;
    private Servo     servo_tavaTwo = null;

    public boolean test = false;

    @Override
    public void init() {
        init_motoare();


    }

    @Override
    public void start() { timp.reset();}

    @Override
    public void stop() { }

    @Override
    public void loop() {
        double spin_up = -gamepad1.right_stick_x;
        double spin = Range.clip(spin_up, -0.7, 0.7);

        if(Math.abs(spin) > .1) {
            left_DriveOne.setPower(spin);
            left_DriveTwo.setPower(spin);
            right_DriveOne.setPower(spin);
            right_DriveTwo.setPower(spin);
        }else {
            double st = -gamepad1.left_stick_y;
            double dr = -gamepad1.right_stick_x; // * -1

            double p1 = Range.clip(st, -0.8, 0.8);
            double p2 = Range.clip(dr, -0.8, 0.8);

            left_DriveOne.setPower(p1);
            left_DriveTwo.setPower(p2);
            right_DriveOne.setPower(-p1);
            right_DriveTwo.setPower(-p2);
        }

        if(gamepad1.left_bumper) {
            servo_autos.setPosition(0.7);
        }
        if(gamepad1.right_bumper)
            servo_autos.setPosition(0.2);

        if(gamepad1.dpad_right) {
            servo_tavaOne.setPosition(0.6);
            servo_tavaOne.setPosition(0.6);
            servo_tavaTwo.setPosition(0.6);
            servo_tavaTwo.setPosition(0.6);
        }
        if(gamepad1.dpad_left) {
            servo_tavaOne.setPosition(0.0);
            servo_tavaOne.setPosition(0.0);
            servo_tavaTwo.setPosition(0.0);
            servo_tavaTwo.setPosition(0.0);
        }

        telemetry.addData("INFO I ", "Pornit de " + timp.toString());
        telemetry.addData("INFO IIa", "Motor Stanga 1 " + left_DriveOne.getPower());
        telemetry.addData("INFO IIb", "Motor Dreapta 1 " + right_DriveTwo.getPower());
        telemetry.addData("INFO IIIa", "Servo Autonom " + servo_autos.getPosition());
        telemetry.addData("INFO IIIb", "Servo Tava Stanga " + servo_tavaOne.getPosition());
        telemetry.addData("INFO IIIc", "Servo Tava Dreapta " + servo_tavaTwo.getPosition());
        telemetry.update();
    }

    public void init_motoare() {
        left_DriveOne = hardwareMap.get(DcMotor.class, "left_d1");
        left_DriveTwo = hardwareMap.get(DcMotor.class, "left_d2");
        right_DriveOne = hardwareMap.get(DcMotor.class, "right_d1");
        right_DriveTwo = hardwareMap.get(DcMotor.class, "right_d2");

        init_servos();
    }

    public void init_servos() {
        servo_autos = hardwareMap.get(Servo.class, "servo_autonomy");
        servo_tavaOne = hardwareMap.get(Servo.class, "servo_tavaone");
        servo_tavaTwo = hardwareMap.get(Servo.class, "servo_tavatwo");
    }

}
