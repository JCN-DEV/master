'use strict';

describe('PgmsAppRetirmntNmine Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockPgmsAppRetirmntNmine;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPgmsAppRetirmntNmine = jasmine.createSpy('MockPgmsAppRetirmntNmine');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'PgmsAppRetirmntNmine': MockPgmsAppRetirmntNmine
        };
        createController = function () {
            $injector.get('$controller')("PgmsAppRetirmntNmineDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'stepApp:pgmsAppRetirmntNmineUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
