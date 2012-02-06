describe('Facebook Integration', function() {
    describe('on logUserIntoFacebook', function() {
        it('should call "handleAuthUser" on successful auth', function() {
            spyOn(FB, 'login').andCallFake(function (callback) {
                callback(
                    {
                        status:'connected',
                        authResponse: {accessToken: 'access_token'}
                    });
            });

            spyOn(Initech, 'handleAuthUser');

            Initech.logUserIntoFacebook();

            expect(Initech.handleAuthUser).toHaveBeenCalled();
        });

        it('should call "handleNoneAuthUser" on successful auth', function() {
            spyOn(FB, 'login').andCallFake(function (callback) {
                callback({});
            });

            spyOn(Initech, 'handleNoneAuthUser');

            Initech.logUserIntoFacebook();

            expect(Initech.handleNoneAuthUser).toHaveBeenCalled();
        });

        it('should also fetch a Facebook users data and call "displayFacebookData"', function() {
            var expectedUserData = {name: 'Milton Waddams'};

            spyOn(FB, 'login').andCallFake(function (callback) {
                callback(
                    {
                        status:'connected',
                        authResponse: {accessToken: 'access_token'}
                    });
            });

            spyOn(FB, 'api').andCallFake(function (path, callback) {
                expect(path).toBe('/me');
                callback(expectedUserData);
            });

            spyOn(Initech, 'displayFacebookData');

            Initech.logUserIntoFacebook();

            expect(Initech.displayFacebookData).toHaveBeenCalledWith(expectedUserData);
        });
    });
});