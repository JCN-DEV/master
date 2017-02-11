'use strict';

describe('PfmsLoanApplication Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPfmsLoanApplication, MockHrEmployeeInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPfmsLoanApplication = jasmine.createSpy('MockPfmsLoanApplication');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'PfmsLoanApplication': MockPfmsLoanApplication,
            'HrEmployeeInfo': MockHrEmployeeInfo
        };
        createController = function() {
            $injector.get('$controller')("PfmsLoanApplicationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:pfmsLoanApplicationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
