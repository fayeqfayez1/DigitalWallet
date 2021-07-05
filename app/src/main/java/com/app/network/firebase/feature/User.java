package com.app.network.firebase.feature;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.app.R;
import com.app.network.RequestListener;
import com.app.network.StringRequestListener;
import com.app.network.firebase.model.CreditCard;
import com.app.network.firebase.model.UserData;
import com.app.utils.App;
import com.app.utils.AppSharedData;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import java.util.ArrayList;
import java.util.Objects;

import static com.app.network.firebase.utils.FirebaseReferances.getAuthCurrentUserReference;
import static com.app.network.firebase.utils.FirebaseReferances.getAuthReference;

import static com.app.network.firebase.utils.FirebaseReferances.getCreditCardListReference;
import static com.app.network.firebase.utils.FirebaseReferances.getCreditCardReference;
import static com.app.network.firebase.utils.FirebaseReferances.getUserId;
import static com.app.network.firebase.utils.FirebaseReferances.getUserReference;


public class User {
    private static String TAG = User.class.getSimpleName();
    private StringRequestListener<UserData> getUserDataListener;
    private static User instance;

    public static User getInstance() {
        if (instance == null)
            instance = new User();
        return instance;
    }

    public static void userSignUp(UserData userData, String password, RequestListener<UserData> requestListener) {
        getAuthReference().createUserWithEmailAndPassword(userData.getEmail(), password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = getAuthCurrentUserReference();
                        userData.setUserId(firebaseUser.getUid());
                        requestListener.onSuccess(userData);
                    } else {
                        requestListener.onFail(taskVoidException2(task));
                    }
                });
    }

    public static void saveUserDataToDatabase(UserData userData, boolean sendEmail, StringRequestListener<UserData> requestListener) {
        getUserReference(userData.getUserId()).setValue(userData.toMap()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (sendEmail) {
                    sendVerificationEmail();
                    requestListener.onSuccess(App.getInstance().getString(R.string.verify_email), userData);
                } else {
                    requestListener.onSuccess("Success", userData);
                }
            } else {
                getAuthReference().signOut();
                requestListener.onFail(task.getException().getMessage());
            }
        });
    }

    public static void saveUserCreditCardDataToDatabase(String userId, CreditCard creditCard, StringRequestListener<CreditCard> requestListener) {
        getCreditCardReference(userId).setValue(creditCard.toMap()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                requestListener.onSuccess(App.getInstance().getString(R.string.credit_card_add_successful), creditCard);
            } else {
                requestListener.onFail(task.getException().getMessage());
            }
        });
    }


    public static void sendVerificationEmail() {
        FirebaseUser user = getAuthReference().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    FirebaseAuth.getInstance().signOut();
                });
    }


    public static boolean checkIfEmailVerified() {
        boolean result = false;
        FirebaseUser user = getAuthReference().getCurrentUser();
        if (user.isEmailVerified()) {
            result = true;
        } else {
            sendVerificationEmail();
            result = false;
        }
        return result;
    }

    public static void loginWithEmailAndPassword(String email, String password, StringRequestListener<String> requestListener) {
        getAuthReference().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = getAuthReference().getCurrentUser().getUid();
                        AppSharedData.setUserId(userId);
                        if (checkIfEmailVerified()) {
                            requestListener.onSuccess(App.getInstance().getString(R.string.update_success), userId);
                            /*
                            getUserData(userId, new StringRequestListener<UserData>() {
                                @Override
                                public void onSuccess(String msg, UserData data) {
                                    getUserReference(data.getUserId()).child("deviceToken").setValue(FirebaseMessaging.getInstance().getToken())
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    requestListener.onSuccess(App.getInstance().getString(R.string.update_success), data);
                                                } else {
                                                    requestListener.onFail(task.getException().getMessage());
                                                }
                                            });
                                }

                                @Override
                                public void onFail(String message) {
                                    requestListener.onFail(message);
                                }
                            });*/
                        } else {
                            requestListener.onSuccess(App.getInstance().getString(R.string.please_verify_email), null);
                        }
                    } else {
                        requestListener.onFail(taskVoidException2(task));
                    }
                });
    }

    public static void getUserData(String userId, final StringRequestListener<UserData> requestListener) {
        getUserReference(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("UserId", userId.toString() + "");
                if (dataSnapshot.exists()) {
                    UserData user = dataSnapshot.getValue(UserData.class);
                    requestListener.onSuccess("", user);
                } else {
                    requestListener.onFail(App.getInstance().getString(R.string.error));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                requestListener.onFail(databaseError.getMessage());
            }
        });
    }

    public static void getAllCreditCardData(RequestListener<ArrayList<CreditCard>> requestListener) {
        ArrayList<CreditCard> dataList = new ArrayList<>();
        String userId = AppSharedData.getUserId();
        getCreditCardListReference().child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.exists()) {
                        CreditCard creditCard = data.getValue(CreditCard.class);
                        dataList.add(creditCard);

                    }

                }
                requestListener.onSuccess(dataList);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                requestListener.onFail(error.toString());
            }
        });
    }


    private ValueEventListener userData = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                UserData user = dataSnapshot.getValue(UserData.class);
                getUserDataListener.onSuccess("", user);
            } else {
                getUserDataListener.onFail(App.getInstance().getString(R.string.error));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            getUserDataListener.onFail(databaseError.getMessage());
        }
    };

    public void startGetUserData(String userId, StringRequestListener<UserData> requestListener) {
        this.getUserDataListener = requestListener;
        getUserReference(userId).addValueEventListener(userData);
    }


    public static void resetPassword(String email, RequestListener<String> requestListener) {
        getAuthReference().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        requestListener.onSuccess("");
                    } else {
                        requestListener.onFail(taskVoidException(task));
                    }
                });
    }

    public static void forgetPassword(String email, RequestListener<String> requestListener) {
        getAuthReference().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        requestListener.onSuccess(App.getInstance().getString(R.string.email_sent));
                    } else {
                        requestListener.onFail(App.getInstance().getString(R.string.account_not_found));
                    }
                });
    }

    private static String taskVoidException(Task<Void> task) {
        try {
            throw Objects.requireNonNull(task.getException());
        } catch (FirebaseAuthWeakPasswordException e) {
            return App.getInstance().getString(R.string.error_week_password);
        } catch (FirebaseAuthInvalidCredentialsException e) {
            return App.getInstance().getString(R.string.pass_email_invalid);
        } catch (FirebaseAuthInvalidUserException e) {
            return App.getInstance().getString(R.string.pass_email_invalid);
        } catch (FirebaseNoSignedInUserException e) {
            return App.getInstance().getString(R.string.emailInvalid);
        } catch (FirebaseAuthEmailException e) {
            return App.getInstance().getString(R.string.emailInvalid);
        } catch (FirebaseAuthUserCollisionException e) {
            return App.getInstance().getString(R.string.ERR_emailExist);
        } catch (Exception e) {
            return App.getInstance().getString(R.string.error);
        }
    }

    private static String taskVoidException2(Task<AuthResult> task) {
        try {
            throw Objects.requireNonNull(task.getException());
        } catch (FirebaseAuthWeakPasswordException e) {
            return App.getInstance().getString(R.string.error_week_password);
        } catch (FirebaseAuthInvalidCredentialsException e) {
            return App.getInstance().getString(R.string.pass_email_invalid);
        } catch (FirebaseAuthInvalidUserException e) {
            return App.getInstance().getString(R.string.pass_email_invalid);
        } catch (FirebaseNoSignedInUserException e) {
            return App.getInstance().getString(R.string.emailInvalid);
        } catch (FirebaseAuthEmailException e) {
            return App.getInstance().getString(R.string.emailInvalid);
        } catch (FirebaseAuthUserCollisionException e) {
            return App.getInstance().getString(R.string.ERR_emailExist);
        } catch (Exception e) {
            return App.getInstance().getString(R.string.error);
        }
    }

    public static void changePassword(String oldPassword, String newPassword, RequestListener<String> requestListener) {
        AuthCredential credential = EmailAuthProvider
                .getCredential(AppSharedData.getUserData().getEmail(), oldPassword);
        // Prompt the user to re-provide their sign-in credentials
        getAuthCurrentUserReference().reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getAuthCurrentUserReference().updatePassword(newPassword).addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                UserData userData = AppSharedData.getUserData();
                                requestListener.onSuccess("done");
                                //  changeUserInDatabase(userData, requestListener);
                            } else {
                                requestListener.onFail(task2.getException().getMessage());
                            }
                        });
                    } else {
                        requestListener.onFail(task.getException().getMessage());
                    }
                });

    }


    private static void logoutUser() {
        getAuthReference().signOut();
        AppSharedData.setUserLogin(false);
        AppSharedData.setUserData(null);
    }


    public static void updateUserData(Activity context, UserData
            userData, RequestListener<UserData> requestListener) {
        if (!TextUtils.isEmpty(getUserId())) {
            getUserReference(getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                        UserData userData = dataSnapshot.getValue(UserData.class);
                        AppSharedData.setUserData(userData);
                        AppSharedData.setUserLogin(true);
                        AppSharedData.setLoginBySocial(true);
                        requestListener.onSuccess(userData);
                    } else {
                        saveUserDataToDatabase(userData, false, new StringRequestListener<UserData>() {
                                    @Override
                                    public void onSuccess(String msg, UserData data) {
                                        requestListener.onSuccess(data);
                                    }

                                    @Override
                                    public void onFail(String message) {
                                        getAuthReference().signOut();
                                        requestListener.onFail(message);
                                    }
                                }
                        );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    getAuthReference().signOut();
                    requestListener.onFail(databaseError.getMessage());
                }
            });
        } else {
            getAuthReference().signOut();
        }
    }


}
