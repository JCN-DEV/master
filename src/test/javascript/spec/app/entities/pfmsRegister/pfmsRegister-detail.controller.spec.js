'use strict';

describe('PfmsRegister Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPfmsRegister, MockHrEmployeeInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPfmsRegister = jasmine.createSpy('MockPfmsRegister');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'PfmsRegister': MockPfmsRegister,
            'HrEmployeeInfo': MockHrEmployeeInfo
        };
        createController = function() {
            $injector.get('$controller')("PfmsRegisterDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:pfmsRegisterUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
