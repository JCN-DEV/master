'use strict';

describe('InstituteShopInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstituteShopInfo, MockInstituteInfraInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstituteShopInfo = jasmine.createSpy('MockInstituteShopInfo');
        MockInstituteInfraInfo = jasmine.createSpy('MockInstituteInfraInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstituteShopInfo': MockInstituteShopInfo,
            'InstituteInfraInfo': MockInstituteInfraInfo
        };
        createController = function() {
            $injector.get('$controller')("InstituteShopInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instituteShopInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
