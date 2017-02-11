'use strict';

describe('InstPlayGroundInfoTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstPlayGroundInfoTemp, MockInstInfraInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstPlayGroundInfoTemp = jasmine.createSpy('MockInstPlayGroundInfoTemp');
        MockInstInfraInfo = jasmine.createSpy('MockInstInfraInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstPlayGroundInfoTemp': MockInstPlayGroundInfoTemp,
            'InstInfraInfo': MockInstInfraInfo
        };
        createController = function() {
            $injector.get('$controller')("InstPlayGroundInfoTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instPlayGroundInfoTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
