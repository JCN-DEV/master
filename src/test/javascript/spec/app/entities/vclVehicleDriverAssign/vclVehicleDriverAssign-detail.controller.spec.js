'use strict';

describe('VclVehicleDriverAssign Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockVclVehicleDriverAssign, MockVclVehicle, MockVclDriver;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockVclVehicleDriverAssign = jasmine.createSpy('MockVclVehicleDriverAssign');
        MockVclVehicle = jasmine.createSpy('MockVclVehicle');
        MockVclDriver = jasmine.createSpy('MockVclDriver');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'VclVehicleDriverAssign': MockVclVehicleDriverAssign,
            'VclVehicle': MockVclVehicle,
            'VclDriver': MockVclDriver
        };
        createController = function() {
            $injector.get('$controller')("VclVehicleDriverAssignDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:vclVehicleDriverAssignUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
