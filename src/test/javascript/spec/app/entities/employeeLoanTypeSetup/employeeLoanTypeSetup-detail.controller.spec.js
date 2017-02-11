'use strict';

describe('EmployeeLoanTypeSetup Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployeeLoanTypeSetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployeeLoanTypeSetup = jasmine.createSpy('MockEmployeeLoanTypeSetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EmployeeLoanTypeSetup': MockEmployeeLoanTypeSetup
        };
        createController = function() {
            $injector.get('$controller')("EmployeeLoanTypeSetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:employeeLoanTypeSetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
