'use strict';

describe('InstLabInfoTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstLabInfoTemp, MockInstInfraInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstLabInfoTemp = jasmine.createSpy('MockInstLabInfoTemp');
        MockInstInfraInfo = jasmine.createSpy('MockInstInfraInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstLabInfoTemp': MockInstLabInfoTemp,
            'InstInfraInfo': MockInstInfraInfo
        };
        createController = function() {
            $injector.get('$controller')("InstLabInfoTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instLabInfoTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
