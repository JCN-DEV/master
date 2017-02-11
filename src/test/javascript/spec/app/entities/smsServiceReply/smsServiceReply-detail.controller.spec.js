'use strict';

describe('SmsServiceReply Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSmsServiceReply, MockSmsServiceComplaint, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSmsServiceReply = jasmine.createSpy('MockSmsServiceReply');
        MockSmsServiceComplaint = jasmine.createSpy('MockSmsServiceComplaint');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SmsServiceReply': MockSmsServiceReply,
            'SmsServiceComplaint': MockSmsServiceComplaint,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("SmsServiceReplyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:smsServiceReplyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
