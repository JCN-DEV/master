'use strict';

describe('SmsServiceForward Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSmsServiceForward, MockSmsServiceComplaint, MockUser, MockSmsServiceDepartment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSmsServiceForward = jasmine.createSpy('MockSmsServiceForward');
        MockSmsServiceComplaint = jasmine.createSpy('MockSmsServiceComplaint');
        MockUser = jasmine.createSpy('MockUser');
        MockSmsServiceDepartment = jasmine.createSpy('MockSmsServiceDepartment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SmsServiceForward': MockSmsServiceForward,
            'SmsServiceComplaint': MockSmsServiceComplaint,
            'User': MockUser,
            'SmsServiceDepartment': MockSmsServiceDepartment
        };
        createController = function() {
            $injector.get('$controller')("SmsServiceForwardDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:smsServiceForwardUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
