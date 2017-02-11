'use strict';

describe('EmployeeLoanBillRegister Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployeeLoanBillRegister, MockInstEmployee, MockEmployeeLoanRequisitionForm;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployeeLoanBillRegister = jasmine.createSpy('MockEmployeeLoanBillRegister');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockEmployeeLoanRequisitionForm = jasmine.createSpy('MockEmployeeLoanRequisitionForm');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EmployeeLoanBillRegister': MockEmployeeLoanBillRegister,
            'InstEmployee': MockInstEmployee,
            'EmployeeLoanRequisitionForm': MockEmployeeLoanRequisitionForm
        };
        createController = function() {
            $injector.get('$controller')("EmployeeLoanBillRegisterDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:employeeLoanBillRegisterUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
