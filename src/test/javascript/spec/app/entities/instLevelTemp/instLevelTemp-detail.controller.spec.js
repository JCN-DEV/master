'use strict';

describe('InstLevelTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstLevelTemp;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstLevelTemp = jasmine.createSpy('MockInstLevelTemp');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstLevelTemp': MockInstLevelTemp
        };
        createController = function() {
            $injector.get('$controller')("InstLevelTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instLevelTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
