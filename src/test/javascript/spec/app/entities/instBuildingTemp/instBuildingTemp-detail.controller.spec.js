'use strict';

describe('InstBuildingTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstBuildingTemp;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstBuildingTemp = jasmine.createSpy('MockInstBuildingTemp');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstBuildingTemp': MockInstBuildingTemp
        };
        createController = function() {
            $injector.get('$controller')("InstBuildingTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instBuildingTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
