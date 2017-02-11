'use strict';

describe('EmployeeLoanCheckRegister Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployeeLoanCheckRegister, MockInstEmployee, MockEmployeeLoanRequisitionForm, MockEmployeeLoanBillRegister;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployeeLoanCheckRegister = jasmine.createSpy('MockEmployeeLoanCheckRegister');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockEmployeeLoanRequisitionForm = jasmine.createSpy('MockEmployeeLoanRequisitionForm');
        MockEmployeeLoanBillRegister = jasmine.createSpy('MockEmployeeLoanBillRegister');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EmployeeLoanCheckRegister': MockEmployeeLoanCheckRegister,
            'InstEmployee': MockInstEmployee,
            'EmployeeLoanRequisitionForm': MockEmployeeLoanRequisitionForm,
            'EmployeeLoanBillRegister': MockEmployeeLoanBillRegister
        };
        createController = function() {
            $injector.get('$controller')("EmployeeLoanCheckRegisterDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:employeeLoanCheckRegisterUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
