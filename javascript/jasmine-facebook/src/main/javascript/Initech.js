var Initech = {
    handleAuthUser : function() {
        // code to do cool Facebook stuff after login
        console.log('Welcome!  Fetching your information.... ');
        Initech.getUserFacebookData();
    },
    handleNoneAuthUser : function() {
        // code to do report to user to log into Facebook
        console.log('User cancelled login or did not fully authorize.');
    },
    logUserIntoFacebook : function() {
        FB.login(function(response) {
            if (response.authResponse) {
                Initech.handleAuthUser();
            } else {
                Initech.handleNoneAuthUser();
            }
        }, {scope: 'email'});
    },
    displayFacebookData : function(userData) {
        // code to display a Users Facebook data
        console.log('Good to see you, ' + userData.name + '.');
    },
    getUserFacebookData : function() {
        FB.api('/me', function(response) {
            Initech.displayFacebookData(response);
        });
    }
};