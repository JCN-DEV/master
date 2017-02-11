'use strict';

describe('SmsServiceAssign Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSmsServiceAssign, MockSmsServiceDepartment, MockEmployee, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSmsServiceAssign = jasmine.createSpy('MockSmsServiceAssign');
        MockSmsServiceDepartment = jasmine.createSpy('MockSmsServiceDepartment');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'SmsServiceAssign': MockSmsServiceAssign,
            'SmsServiceDepartment': MockSmsServiceDepartment,
            'Employee': MockEmployee,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("SmsServiceAssignDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:smsServiceAssignUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
