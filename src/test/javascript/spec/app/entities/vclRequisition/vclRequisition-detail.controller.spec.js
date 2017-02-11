'use strict';

describe('VclRequisition Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockVclRequisition, MockUser, MockVclVehicle, MockVclDriver;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockVclRequisition = jasmine.createSpy('MockVclRequisition');
        MockUser = jasmine.createSpy('MockUser');
        MockVclVehicle = jasmine.createSpy('MockVclVehicle');
        MockVclDriver = jasmine.createSpy('MockVclDriver');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'VclRequisition': MockVclRequisition,
            'User': MockUser,
            'VclVehicle': MockVclVehicle,
            'VclDriver': MockVclDriver
        };
        createController = function() {
            $injector.get('$controller')("VclRequisitionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:vclRequisitionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
