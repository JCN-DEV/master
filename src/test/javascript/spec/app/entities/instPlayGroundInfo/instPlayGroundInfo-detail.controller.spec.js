'use strict';

describe('InstPlayGroundInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstPlayGroundInfo, MockInstInfraInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstPlayGroundInfo = jasmine.createSpy('MockInstPlayGroundInfo');
        MockInstInfraInfo = jasmine.createSpy('MockInstInfraInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstPlayGroundInfo': MockInstPlayGroundInfo,
            'InstInfraInfo': MockInstInfraInfo
        };
        createController = function() {
            $injector.get('$controller')("InstPlayGroundInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instPlayGroundInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
