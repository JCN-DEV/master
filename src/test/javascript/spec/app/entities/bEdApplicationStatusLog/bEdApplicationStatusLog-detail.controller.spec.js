'use strict';

describe('BEdApplicationStatusLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockBEdApplicationStatusLog, MockBEdApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockBEdApplicationStatusLog = jasmine.createSpy('MockBEdApplicationStatusLog');
        MockBEdApplication = jasmine.createSpy('MockBEdApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'BEdApplicationStatusLog': MockBEdApplicationStatusLog,
            'BEdApplication': MockBEdApplication
        };
        createController = function() {
            $injector.get('$controller')("BEdApplicationStatusLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:bEdApplicationStatusLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
