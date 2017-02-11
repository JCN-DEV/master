'use strict';

describe('JobPlacementInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJobPlacementInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJobPlacementInfo = jasmine.createSpy('MockJobPlacementInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JobPlacementInfo': MockJobPlacementInfo
        };
        createController = function() {
            $injector.get('$controller')("JobPlacementInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jobPlacementInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
