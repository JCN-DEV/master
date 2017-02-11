'use strict';

describe('EmployeeLoanRequisitionForm Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployeeLoanRequisitionForm, MockEmployeeLoanTypeSetup, MockEmployeeLoanRulesSetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployeeLoanRequisitionForm = jasmine.createSpy('MockEmployeeLoanRequisitionForm');
        MockEmployeeLoanTypeSetup = jasmine.createSpy('MockEmployeeLoanTypeSetup');
        MockEmployeeLoanRulesSetup = jasmine.createSpy('MockEmployeeLoanRulesSetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EmployeeLoanRequisitionForm': MockEmployeeLoanRequisitionForm,
            'EmployeeLoanTypeSetup': MockEmployeeLoanTypeSetup,
            'EmployeeLoanRulesSetup': MockEmployeeLoanRulesSetup
        };
        createController = function() {
            $injector.get('$controller')("EmployeeLoanRequisitionFormDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:employeeLoanRequisitionFormUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
