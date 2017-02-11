'use strict';

describe('PgmsPenGrSetup Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockPgmsPenGrSetup;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPgmsPenGrSetup = jasmine.createSpy('MockPgmsPenGrSetup');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'PgmsPenGrSetup': MockPgmsPenGrSetup
        };
        createController = function () {
            $injector.get('$controller')("PgmsPenGrSetupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'stepApp:pgmsPenGrSetupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
