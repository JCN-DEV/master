'use strict';

describe('InstComiteFormationTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstComiteFormationTemp, MockInstMemShip;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstComiteFormationTemp = jasmine.createSpy('MockInstComiteFormationTemp');
        MockInstMemShip = jasmine.createSpy('MockInstMemShip');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstComiteFormationTemp': MockInstComiteFormationTemp,
            'InstMemShip': MockInstMemShip
        };
        createController = function() {
            $injector.get('$controller')("InstComiteFormationTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instComiteFormationTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
