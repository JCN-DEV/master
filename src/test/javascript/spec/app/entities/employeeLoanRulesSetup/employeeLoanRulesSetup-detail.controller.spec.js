'use strict';

describe('EmployeeLoanRulesSetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployeeLoanRulesSetup, MockEmployeeLoanTypeSetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployeeLoanRulesSetup = jasmine.createSpy('MockEmployeeLoanRulesSetup');
        MockEmployeeLoanTypeSetup = jasmine.createSpy('MockEmployeeLoanTypeSetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EmployeeLoanRulesSetup': MockEmployeeLoanRulesSetup,
            'EmployeeLoanTypeSetup': MockEmployeeLoanTypeSetup
        };
        createController = function() {
            $injector.get('$controller')("EmployeeLoanRulesSetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:employeeLoanRulesSetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
