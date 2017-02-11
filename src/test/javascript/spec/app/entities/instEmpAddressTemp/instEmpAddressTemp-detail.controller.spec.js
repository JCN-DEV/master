'use strict';

describe('InstEmpAddressTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmpAddressTemp, MockInstEmployee, MockUpazila;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmpAddressTemp = jasmine.createSpy('MockInstEmpAddressTemp');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockUpazila = jasmine.createSpy('MockUpazila');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmpAddressTemp': MockInstEmpAddressTemp,
            'InstEmployee': MockInstEmployee,
            'Upazila': MockUpazila
        };
        createController = function() {
            $injector.get('$controller')("InstEmpAddressTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmpAddressTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
