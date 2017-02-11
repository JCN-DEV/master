'use strict';

describe('InstComiteFormation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstComiteFormation, MockInstMemShip;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstComiteFormation = jasmine.createSpy('MockInstComiteFormation');
        MockInstMemShip = jasmine.createSpy('MockInstMemShip');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstComiteFormation': MockInstComiteFormation,
            'InstMemShip': MockInstMemShip
        };
        createController = function() {
            $injector.get('$controller')("InstComiteFormationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instComiteFormationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
