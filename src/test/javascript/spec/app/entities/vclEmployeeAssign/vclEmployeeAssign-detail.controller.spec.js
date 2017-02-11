'use strict';

describe('VclEmployeeAssign Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockVclEmployeeAssign, MockVclVehicle, MockEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockVclEmployeeAssign = jasmine.createSpy('MockVclEmployeeAssign');
        MockVclVehicle = jasmine.createSpy('MockVclVehicle');
        MockEmployee = jasmine.createSpy('MockEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'VclEmployeeAssign': MockVclEmployeeAssign,
            'VclVehicle': MockVclVehicle,
            'Employee': MockEmployee
        };
        createController = function() {
            $injector.get('$controller')("VclEmployeeAssignDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:vclEmployeeAssignUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
