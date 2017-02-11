'use strict';

describe('MpoApplicationStatusLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoApplicationStatusLog, MockMpoApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoApplicationStatusLog = jasmine.createSpy('MockMpoApplicationStatusLog');
        MockMpoApplication = jasmine.createSpy('MockMpoApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoApplicationStatusLog': MockMpoApplicationStatusLog,
            'MpoApplication': MockMpoApplication
        };
        createController = function() {
            $injector.get('$controller')("MpoApplicationStatusLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoApplicationStatusLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
