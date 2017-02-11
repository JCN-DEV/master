'use strict';

describe('SmsServiceComplaint Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSmsServiceComplaint, MockSmsServiceName, MockSmsServiceType, MockSmsServiceDepartment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSmsServiceComplaint = jasmine.createSpy('MockSmsServiceComplaint');
        MockSmsServiceName = jasmine.createSpy('MockSmsServiceName');
        MockSmsServiceType = jasmine.createSpy('MockSmsServiceType');
        MockSmsServiceDepartment = jasmine.createSpy('MockSmsServiceDepartment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SmsServiceComplaint': MockSmsServiceComplaint,
            'SmsServiceName': MockSmsServiceName,
            'SmsServiceType': MockSmsServiceType,
            'SmsServiceDepartment': MockSmsServiceDepartment
        };
        createController = function() {
            $injector.get('$controller')("SmsServiceComplaintDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:smsServiceComplaintUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
