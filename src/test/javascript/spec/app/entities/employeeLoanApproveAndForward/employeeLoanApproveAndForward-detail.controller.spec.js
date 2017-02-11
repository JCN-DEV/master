'use strict';

describe('EmployeeLoanApproveAndForward Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployeeLoanApproveAndForward, MockEmployeeLoanRequisitionForm, MockAuthority;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployeeLoanApproveAndForward = jasmine.createSpy('MockEmployeeLoanApproveAndForward');
        MockEmployeeLoanRequisitionForm = jasmine.createSpy('MockEmployeeLoanRequisitionForm');
        MockAuthority = jasmine.createSpy('MockAuthority');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EmployeeLoanApproveAndForward': MockEmployeeLoanApproveAndForward,
            'EmployeeLoanRequisitionForm': MockEmployeeLoanRequisitionForm,
            'Authority': MockAuthority
        };
        createController = function() {
            $injector.get('$controller')("EmployeeLoanApproveAndForwardDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:employeeLoanApproveAndForwardUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
