'use strict';

describe('InstEmpSpouseInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmpSpouseInfo, MockInstEmployee, MockInstEmpAddress;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmpSpouseInfo = jasmine.createSpy('MockInstEmpSpouseInfo');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockInstEmpAddress = jasmine.createSpy('MockInstEmpAddress');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmpSpouseInfo': MockInstEmpSpouseInfo,
            'InstEmployee': MockInstEmployee,
            'InstEmpAddress': MockInstEmpAddress
        };
        createController = function() {
            $injector.get('$controller')("InstEmpSpouseInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmpSpouseInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
