'use strict';

describe('InstShopInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstShopInfo, MockInstInfraInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstShopInfo = jasmine.createSpy('MockInstShopInfo');
        MockInstInfraInfo = jasmine.createSpy('MockInstInfraInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstShopInfo': MockInstShopInfo,
            'InstInfraInfo': MockInstInfraInfo
        };
        createController = function() {
            $injector.get('$controller')("InstShopInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instShopInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
