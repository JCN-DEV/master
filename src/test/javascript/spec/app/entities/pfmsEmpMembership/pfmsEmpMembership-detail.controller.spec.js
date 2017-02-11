'use strict';

describe('PfmsEmpMembership Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPfmsEmpMembership, MockHrEmployeeInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPfmsEmpMembership = jasmine.createSpy('MockPfmsEmpMembership');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'PfmsEmpMembership': MockPfmsEmpMembership,
            'HrEmployeeInfo': MockHrEmployeeInfo
        };
        createController = function() {
            $injector.get('$controller')("PfmsEmpMembershipDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:pfmsEmpMembershipUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
