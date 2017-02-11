'use strict';

describe('InstEmpAddress Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmpAddress, MockUpazila, MockInstEmployee, MockInstEmpSpouseInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmpAddress = jasmine.createSpy('MockInstEmpAddress');
        MockUpazila = jasmine.createSpy('MockUpazila');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockInstEmpSpouseInfo = jasmine.createSpy('MockInstEmpSpouseInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmpAddress': MockInstEmpAddress,
            'Upazila': MockUpazila,
            'InstEmployee': MockInstEmployee,
            'InstEmpSpouseInfo': MockInstEmpSpouseInfo
        };
        createController = function() {
            $injector.get('$controller')("InstEmpAddressDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmpAddressUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
