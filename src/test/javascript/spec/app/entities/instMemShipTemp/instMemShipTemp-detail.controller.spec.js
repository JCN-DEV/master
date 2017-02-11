'use strict';

describe('InstMemShipTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstMemShipTemp;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstMemShipTemp = jasmine.createSpy('MockInstMemShipTemp');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstMemShipTemp': MockInstMemShipTemp
        };
        createController = function() {
            $injector.get('$controller')("InstMemShipTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instMemShipTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
