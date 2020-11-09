package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

@TeleOp(name="Joysticks Control V0.1.3", group="Iterative Opmode")

public class joystick extends OpMode {

    // BACKUP
    private ElapsedTime timp = new ElapsedTime();

    // MOTOARE
    private DcMotor   leftDrive = null;
    private DcMotor   rightDrive = null;
    private DcMotor   bratDrive = null;

    // SERVO
    private Servo  servoBrat = null;
    private Servo  servoBrat2 = null;

    // VARIABILE
    public boolean status_FATA = false;
    public boolean status_SPATE = false;
    public boolean bigSpeed = false;
    public boolean servo_A = false;
    public boolean servo_Aa = false;
    public boolean servo_B = false;
    public boolean servo_Bb = false;
    public boolean servo_CupStone = false;

    // SERVO STUFF
    static final double INCREMENT   = 0.05;
    static final double MAX_POS     =  1.0;
    static final double MIN_POS     =  0.0;
    double position = 0.5;
    double Clow = 0;

    @Override
    public void init() {
        init_motoare();

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        bratDrive.setDirection(DcMotor.Direction.FORWARD);
        servoBrat.setDirection(Servo.Direction.FORWARD);
        servoBrat2.setDirection(Servo.Direction.REVERSE);
        servoBrat.setPosition(position);
        servoBrat2.setPosition(position);
    }

    @Override
    public void start() { timp.reset();}

    @Override
    public void stop() { }

    @Override
    public void loop() {

        // START LOCOMOTIE PLAYER1
        double viteza = -gamepad1.left_stick_y, directie = gamepad1.right_stick_x; // era cu -gamepad1.left
        double p1 = Range.clip(viteza + directie, -0.7, 0.7);
        double p2 = Range.clip(viteza - directie, -0.7, 0.7);
        leftDrive.setPower(p1);     rightDrive.setPower(p2);;

        if(gamepad1.left_bumper) bigSpeed = true;
        if(bigSpeed) {
            do {
                leftDrive.setPower(0.9);
                rightDrive.setPower(0.9);
                if(!gamepad1.left_bumper) bigSpeed = !bigSpeed;
            }while(gamepad1.left_bumper);
        }
        if(!bigSpeed) {  leftDrive.setPower(p1); rightDrive.setPower(p2);  }
        // STOP LOCOMOTIE PLAYER1

        // START BRAT PLAYER 2
        if(gamepad2.left_bumper) status_FATA = true;
        if(status_FATA && !status_SPATE) {
             do {
                bratDrive.setPower(0.4);
                if(!gamepad2.left_bumper) status_FATA = !status_FATA;
            }while(gamepad2.left_bumper && status_FATA);
        }
        if(!status_FATA) bratDrive.setPower(0.0);

        if(gamepad2.right_bumper) status_SPATE = true;
        if(status_SPATE && !status_FATA) {
            do {
                bratDrive.setPower(-0.4);
                if(!gamepad2.right_bumper) status_SPATE = !status_SPATE;
            }while(gamepad2.right_bumper && status_SPATE);
        }
        if(!status_SPATE) bratDrive.setPower(0.0);

        // servo
        if(gamepad2.a) servo_A = true;
        if(servo_A && !servo_B) {
            servo_A = false; servo_Aa = true;
            do {
                position += 0.01;
                if(position >= 0.8) position = 0.9;
                servo_A = false; servo_Aa = true;
            }while(gamepad2.a && servo_A);
        }
        if(!servo_A && servo_Aa) { servoBrat.setPosition(position); servo_Aa = false; }

        if(gamepad2.b) servo_B = true;
        if(servo_B && !servo_A) {
            servo_B = false; servo_Bb = true;
            do {
                position -= 0.01;
                if(position <= 0.2) position = 0.2;
                servo_B = false; servo_Bb = true;
            }while(gamepad2.b && servo_B);
        }
        if(!servo_B && servo_Bb) { servoBrat.setPosition(position); servo_Bb = false; }

        if(gamepad2.y) {
            servoBrat.setPosition(0.0);
        }

        // servo
        if(gamepad2.dpad_up) {
            servoBrat2.setPosition(0.6);
        }
        else if(gamepad2.dpad_down) servoBrat2.setPosition(0.0);

        telemetry.addData("INFO I ", "Pornit de " + timp.toString());
        telemetry.addData("INFO IIa", "ServoBrat STANGA Pozitie: " + servoBrat.getPosition());
        telemetry.addData("INFO IIb", "ServoBrat DREAPTA Pozitie: " + servoBrat2.getPosition());
        telemetry.addData("valuePOSITIon: ", "." + position, "VAL A -> " + servo_Aa + " | VAL B " + servo_Bb);
        telemetry.update();
    }

    public void init_motoare() {
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_Drive");
        bratDrive = hardwareMap.get(DcMotor.class, "brat_drive");

        servoBrat = hardwareMap.get(Servo.class, "servo_brat");
        servoBrat2 = hardwareMap.get(Servo.class, "servo_bratdoi");


        //init_servos();
    }

    public void init_servos() {
        servoBrat = hardwareMap.get(Servo.class, "servo_brat");
        servoBrat2 = hardwareMap.get(Servo.class, "servo_bratdoi");
    }



}
