'use strict';

describe('SmsServiceDepartment Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSmsServiceDepartment, MockUser, MockSmsServiceType, MockSmsServiceName, MockDepartment;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSmsServiceDepartment = jasmine.createSpy('MockSmsServiceDepartment');
        MockUser = jasmine.createSpy('MockUser');
        MockSmsServiceType = jasmine.createSpy('MockSmsServiceType');
        MockSmsServiceName = jasmine.createSpy('MockSmsServiceName');
        MockDepartment = jasmine.createSpy('MockDepartment');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SmsServiceDepartment': MockSmsServiceDepartment,
            'User': MockUser,
            'SmsServiceType': MockSmsServiceType,
            'SmsServiceName': MockSmsServiceName,
            'Department': MockDepartment
        };
        createController = function() {
            $injector.get('$controller')("SmsServiceDepartmentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:smsServiceDepartmentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
