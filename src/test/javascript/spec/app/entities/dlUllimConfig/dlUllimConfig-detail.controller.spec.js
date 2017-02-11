'use strict';

describe('DlUllimConfig Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlUllimConfig;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlUllimConfig = jasmine.createSpy('MockDlUllimConfig');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlUllimConfig': MockDlUllimConfig
        };
        createController = function() {
            $injector.get('$controller')("DlUllimConfigDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlUllimConfigUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
