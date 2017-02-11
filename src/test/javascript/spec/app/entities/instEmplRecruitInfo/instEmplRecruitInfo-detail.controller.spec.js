'use strict';

describe('InstEmplRecruitInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmplRecruitInfo, MockInstEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmplRecruitInfo = jasmine.createSpy('MockInstEmplRecruitInfo');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmplRecruitInfo': MockInstEmplRecruitInfo,
            'InstEmployee': MockInstEmployee
        };
        createController = function() {
            $injector.get('$controller')("InstEmplRecruitInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmplRecruitInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
