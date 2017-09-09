package com.clock50.pryce.UI;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.clock50.pryce.R;
import com.clock50.pryce.SRC.managers.LoginManager;

public class SignActivity extends AppCompatActivity {


    /* ************************************************************************* *
     *                                                                           *
     * Instance Variables                                                        *
     *                                                                           *
     * ************************************************************************* */

    /** Horizontal bar that moves under the 'Sign In' and 'Sign Up' buttons when they're clicked */
    private ImageView sign_slider;

    /** The button ('Sign In' or 'Sign Up') that is currently selected */
    private ImageView btn_selected;

    /** The label on the 'Sign' button. */
    private TextView lbl_sign;

    /** Text label to display an error */
    private TextView lbl_error;

    /** Text field for the email */
    private EditText txtfld_email;

    /** Text field for the password */
    private EditText txtfld_password;

    /** The button used to sign the user in */
    private ImageView btn_sign;

    /** Progress bar display on top of the button while signing the user in */
    private ProgressBar pbar_sign;

    /** The type of sign the user is attempting to do (ie. SIGN UP or SIGN IN)*/
    private SignType sign_type;

    /** Checkbox indicating to keep the user signed in if checked */
    private CheckBox check_box_sign;


    /* ************************************************************************* *
     *                                                                           *
     * Constructors                                                              *
     *                                                                           *
     * ************************************************************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* If the user has already logged in, then take him straight there */
        SharedPreferences sp = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        if(sp.contains("_email") && sp.contains("_password")){
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

        setContentView(R.layout.activity_sign);
        initializeViews();
        //
    }


    /* ************************************************************************* *
     *                                                                           *
     * Helper Instance Methods                                                   *
     *                                                                           *
     * ************************************************************************* */

    /**
     * Initializes all the views of this activity.
     */
    private void initializeViews(){
        sign_slider = (ImageView) findViewById(R.id.sign_bar);
        lbl_sign = (TextView) findViewById(R.id.lbl_sign);
        lbl_error= (TextView) findViewById(R.id.lbl_error);
        txtfld_email = (EditText) findViewById(R.id.txtfld_email);
        txtfld_password = (EditText) findViewById(R.id.txtfld_password);
        btn_sign = (ImageView) findViewById(R.id.btn_sign);
        pbar_sign = (ProgressBar) findViewById(R.id.pbar_sign);
        check_box_sign = (CheckBox) findViewById(R.id.check_box_sign);

        txtfld_email.requestFocus();

        /* Initialize the selected button to the 'Sign In' button*/
        btn_selected = (ImageView) findViewById(R.id.btn_signin);
        btn_selected.setEnabled(false);
        ((ImageView)findViewById(R.id.btn_signup)).setAlpha(126);

        sign_type = SignType.SIGN_IN;
    }


    /* ************************************************************************* *
     *                                                                           *
     * Animation Instance Methods                                                *
     *                                                                           *
     * ************************************************************************  */

    /**
     * Upon selecting 'Sign In' or 'Sign Up', the sign slider will move to the
     * selected button and the opacity will change accordingly.
     *
     * @param view the button that was clicked
     */
    private void animateSignSelection(View view){
        /* Animate and perform UI changes */
        ImageView pressed_btn = (ImageView) view;
        sign_slider.animate().x(pressed_btn.getX()).start();
        pressed_btn.setEnabled(false);
        btn_selected.setEnabled(true);

        pressed_btn.setAlpha(255);
        btn_selected.setAlpha(126);

        btn_selected = pressed_btn;
    }

    /** Represents to type of signing the user is attempting to do in the activity */
    private enum SignType{
        SIGN_IN, SIGN_UP
    }

    /**
     * Changes the 'Sign' button's colour based on the selected sign type
     * ('Sign In' (red) or 'Sign Up' (blue))
     */
    private void animateSignBtnColour(SignType sign_type){
        if(sign_type == SignType.SIGN_IN){
            /* ObjectAnimator animates the view calling the method "set" with the second argument
            * appended onto it, using ArgbEvaluator, and going from Color 1 to Color 2. */
            ObjectAnimator bg_animation = ObjectAnimator.ofObject(btn_sign, "ColorFilter",
                    new ArgbEvaluator(), Color.argb(255, 0, 125, 197), Color.argb(255, 227, 38, 26));
            bg_animation.setDuration(300);
            bg_animation.start();
        }
        else{
            ObjectAnimator bg_animation = ObjectAnimator.ofObject(btn_sign, "ColorFilter",
                    new ArgbEvaluator(), Color.argb(255, 227, 38, 26), Color.argb(255, 0, 125, 197));
            bg_animation.setDuration(300);
            bg_animation.start();
        }
    }

    /* ************************************************************************* *
     *                                                                           *
     * Button Instance Methods                                                   *
     *                                                                           *
     * ************************************************************************  */

    public void onSignInBtnClick(View view){

        lbl_sign.setText("SIGN IN");
        animateSignSelection(view);
        sign_type = SignType.SIGN_IN;
        animateSignBtnColour(sign_type);

    }

    public void onSignUpBtnClick(View view){

        lbl_sign.setText("SIGN UP");
        animateSignSelection(view);
        sign_type = SignType.SIGN_UP;
        animateSignBtnColour(sign_type);
    }

    public void onSignBtnClick(View view){

        /* Show progress bar */
        pbar_sign.setVisibility(View.VISIBLE);
        lbl_sign.setVisibility(View.INVISIBLE);

        /* Disable the text fields */
        txtfld_email.setEnabled(false);
        txtfld_password.setEnabled(false);

        /* Set the error response if there is an error in signing in */
        LoginManager.getInstance().login_response = response -> {

            /* UnShow progress bar */
            pbar_sign.setVisibility(View.INVISIBLE);
            lbl_sign.setVisibility(View.VISIBLE);

            /* Disable the text fields */
            txtfld_email.setEnabled(true);
            txtfld_password.setEnabled(true);

            /* If an empty response is given, then there was a successful login or registeration */
            if(response.equals("")){

                /* Save the user's email and hashed pass in sharedPrefs if the user wants it */
                if(check_box_sign.isChecked()) {
                    LoginManager.getInstance().saveUser(getApplicationContext(), txtfld_email.getText().toString(),
                            txtfld_password.getText().toString());
                }

                /* Go to the home activity */
                startActivity(new Intent(getApplicationContext(), Home.class));

            }
            /* Otherwise, an error occurred, so display it */
            else {
                lbl_error.setText(response);
            }
        };

        /* If the user is trying to sign in, then attempt to sign in */
        if(sign_type == SignType.SIGN_IN){
            LoginManager.getInstance().loginUser(getApplicationContext(), txtfld_email.getText().toString(),
                    txtfld_password.getText().toString());
        }
        /* If the user is trying to sign up, then attempt to sign up */
        else if(sign_type == SignType.SIGN_UP){
            LoginManager.getInstance().registerUser(getApplicationContext(), txtfld_email.getText().toString(),
                    txtfld_password.getText().toString());
        }

    }
}

