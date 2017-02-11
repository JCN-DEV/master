'use strict';

describe('PgmsPenGrRate Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockPgmsPenGrRate;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPgmsPenGrRate = jasmine.createSpy('MockPgmsPenGrRate');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'PgmsPenGrRate': MockPgmsPenGrRate
        };
        createController = function () {
            $injector.get('$controller')("PgmsPenGrRateDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'stepApp:pgmsPenGrRateUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
