'use strict';

describe('EmployeeLoanMonthlyDeduction Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployeeLoanMonthlyDeduction, MockInstEmployee, MockEmployeeLoanRequisitionForm;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployeeLoanMonthlyDeduction = jasmine.createSpy('MockEmployeeLoanMonthlyDeduction');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockEmployeeLoanRequisitionForm = jasmine.createSpy('MockEmployeeLoanRequisitionForm');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EmployeeLoanMonthlyDeduction': MockEmployeeLoanMonthlyDeduction,
            'InstEmployee': MockInstEmployee,
            'EmployeeLoanRequisitionForm': MockEmployeeLoanRequisitionForm
        };
        createController = function() {
            $injector.get('$controller')("EmployeeLoanMonthlyDeductionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:employeeLoanMonthlyDeductionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
