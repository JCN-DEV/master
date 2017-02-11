'use strict';

describe('InstLandTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstLandTemp;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstLandTemp = jasmine.createSpy('MockInstLandTemp');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstLandTemp': MockInstLandTemp
        };
        createController = function() {
            $injector.get('$controller')("InstLandTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instLandTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
