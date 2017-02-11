'use strict';

describe('InstShopInfoTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstShopInfoTemp, MockInstInfraInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstShopInfoTemp = jasmine.createSpy('MockInstShopInfoTemp');
        MockInstInfraInfo = jasmine.createSpy('MockInstInfraInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstShopInfoTemp': MockInstShopInfoTemp,
            'InstInfraInfo': MockInstInfraInfo
        };
        createController = function() {
            $injector.get('$controller')("InstShopInfoTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instShopInfoTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
